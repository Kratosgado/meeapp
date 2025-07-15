package com.kratosgado.meeapp.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// id
// title
// content
// image
// groupId
// interests
// userId

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

	@Column(nullable = false)
	private String content;

	@Column()
	private String imageUrl;

	// @ForeignKey()
	private String userId;

	private String groupId;

	private List<String> interests;
}
