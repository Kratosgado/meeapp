package com.kratosgado.meeapp.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "groups")
public class Group {

	public Group(String name, User creator) {
		this.name = name;
		this.creator = creator;
		this.users.add(creator);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "is_public")
	private Boolean isPublic = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id", nullable = false)
	private User creator;

	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	protected Date createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	protected Date updatedAt;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Message> messages = new HashSet<>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Post> posts = new HashSet<>();
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Event> events = new HashSet<>();
}
