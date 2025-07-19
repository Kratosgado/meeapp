package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, String> {

    List<Post> findByGroupIdOrderByCreatedAtDesc(String groupId);

    Page<Post> findByGroupIdOrderByCreatedAtDesc(String groupId, Pageable pageable);

    List<Post> findByUserIdOrderByCreatedAtDesc(String userId);

    @Query("SELECT p FROM Post p WHERE p.group.id = :groupId ORDER BY p.createdAt DESC")
    List<Post> findRecentPostsByGroup(@Param("groupId") String groupId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.group.id = :groupId")
    Long countPostsByGroup(@Param("groupId") String groupId);

    @Query("SELECT p FROM Post p WHERE :interest MEMBER OF p.interests")
    List<Post> findByInterest(@Param("interest") String interest);
}
