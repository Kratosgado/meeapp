package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Group creation data")
public class CreateGroupDto {
	@Schema(description = "Group name", example = "Tech Enthusiasts", required = true)
	private String name;

	@Schema(description = "Group description", example = "A group for technology enthusiasts to share ideas and discuss latest trends")
	private String description;

	@Schema(description = "Whether the group is public", example = "true", defaultValue = "true")
	private Boolean isPublic;
}
