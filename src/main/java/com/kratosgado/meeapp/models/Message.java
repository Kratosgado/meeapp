package com.kratosgado.meeapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;

	@Column
	private String image;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Date createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;
	
	public Message(String text, Group group, User user) {
		this.text = text;
		this.group = group;
		this.user = user;
	}
}
