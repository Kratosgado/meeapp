package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.Event;
import com.kratosgado.meeapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findByCreator(User creator);

    List<Event> findByGroupId(String groupId);

    List<Event> findByIsPublicTrue();

    List<Event> findByEventDateAfter(Timestamp currentTime);

    @Query("SELECT e FROM Event e WHERE e.isPublic = true AND e.eventDate > :currentTime ORDER BY e.eventDate ASC")
    List<Event> findUpcomingPublicEvents(@Param("currentTime") Timestamp currentTime);

    @Query("SELECT e FROM Event e JOIN e.participants p WHERE p.id = :userId")
    List<Event> findEventsByParticipant(@Param("userId") String userId);

    @Query("SELECT e FROM Event e WHERE e.creator.id = :userId OR :userId IN (SELECT p.id FROM e.participants p)")
    List<Event> findEventsByUserInvolvement(@Param("userId") String userId);

    @Query("SELECT e FROM Event e WHERE e.group.id = :groupId AND e.eventDate > :currentTime ORDER BY e.eventDate ASC")
    List<Event> findUpcomingGroupEvents(@Param("groupId") String groupId, @Param("currentTime") Timestamp currentTime);
}