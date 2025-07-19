package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Schema(description = "Group response data")
public class GroupResponseDto {
	@Schema(description = "Group ID", example = "123e4567-e89b-12d3-a456-426614174000")
	private String id;

	@Schema(description = "Group name", example = "Tech Enthusiasts")
	private String name;

	@Schema(description = "Group description", example = "A group for technology enthusiasts to share ideas and discuss latest trends")
	private String description;

	@Schema(description = "Whether the group is public", example = "true")
	private Boolean isPublic;

	@Schema(description = "Group creator information")
	private UserDto creator;

	@Schema(description = "Group members")
	private List<UserDto> users;

	@Schema(description = "Group creation date")
	private Date createdAt;

	@Schema(description = "Group last update date")
	private Date updatedAt;

	@Schema(description = "Number of group members", example = "25")
	private Long memberCount;

	@Schema(description = "Number of messages in the group", example = "150")
	private Long messageCount;

	@Schema(description = "Number of posts in the group", example = "75")
	private Long postCount;

	@Schema(description = "Number of events in the group", example = "10")
	private Long eventCount;
}
