package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, String> {

    List<Message> findByGroupIdOrderByCreatedAtDesc(String groupId);

    Page<Message> findByGroupIdOrderByCreatedAtDesc(String groupId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.group.id = :groupId ORDER BY m.createdAt DESC")
    List<Message> findRecentMessagesByGroup(@Param("groupId") String groupId, Pageable pageable);

    List<Message> findByUserIdOrderByCreatedAtDesc(String userId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.group.id = :groupId")
    Long countMessagesByGroup(@Param("groupId") String groupId);
}
