package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Group;
import com.kratosgado.meeapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group, String> {

  Optional<Group> findByName(String name);

  List<Group> findByIsPublicTrue();

  List<Group> findByCreator(User creator);

  @Query("SELECT g FROM Group g JOIN g.users u WHERE u.id = :userId")
  List<Group> findGroupsByUser(@Param("userId") String userId);

  @Query("SELECT COUNT(g) FROM Group g JOIN g.users u WHERE u.id = :userId")
  Long countGroupsByUser(@Param("userId") String userId);

  @Query("SELECT COUNT(g) FROM Group g WHERE g.creator.id = :userId")
  Long countGroupsByCreator(@Param("userId") String userId);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO user_groups (group_id, user_id) VALUES (:groupId, :userId)", nativeQuery = true)
  void addUserToGroup(@Param("groupId") String groupId, @Param("userId") String userId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user_groups WHERE group_id = :groupId AND user_id = :userId", nativeQuery = true)
  void removeUserFromGroup(@Param("groupId") String groupId, @Param("userId") String userId);

  @Query("SELECT CASE WHEN COUNT(ug) > 0 THEN true ELSE false END FROM Group g JOIN g.users ug WHERE g.id = :groupId AND ug.id = :userId")
  Boolean isUserInGroup(@Param("groupId") String groupId, @Param("userId") String userId);
}
