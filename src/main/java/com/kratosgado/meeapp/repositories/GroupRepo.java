package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface GroupRepo extends JpaRepository<Group, String> {
  @Modifying
  @Transactional
  @Query(
      value = "INSERT INTO user_groups (group_id, user_id) VALUES " + "(:groupId, :userId)",
      nativeQuery = true)
  void addUserToGroup(@Param("groupId") String groupId, @Param("userId") String userId);
}
