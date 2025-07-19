package com.kratosgado.meeapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "events")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "event_date", nullable = false)
    private Timestamp eventDate;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "max_participants")
    private Integer maxParticipants;
    
    @Column(name = "is_public")
    private Boolean isPublic = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "event_participants",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "event_interests", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "interests")
    private Set<String> interests = new HashSet<>();
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
    public Event(String title, String description, Timestamp eventDate, String location, User creator) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.creator = creator;
    }
} 