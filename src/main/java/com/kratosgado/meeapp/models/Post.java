package com.kratosgado.meeapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column()
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@ElementCollection
	@CollectionTable(name = "post_interests", joinColumns = @JoinColumn(name = "post_id"))
	@Column(name = "interests")
	private Set<String> interests;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Date createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;
	
	public Post(String content, User user, Group group) {
		this.content = content;
		this.user = user;
		this.group = group;
	}
}
