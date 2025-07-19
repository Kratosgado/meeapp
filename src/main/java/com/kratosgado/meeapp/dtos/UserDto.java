package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User data transfer object")
public class UserDto {
	@Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
	private String id;

	@Schema(description = "User's full name", example = "John Doe")
	private String name;

	@Schema(description = "User's email address", example = "john.doe@example.com")
	private String email;

	@Schema(description = "User's profile picture URL", example = "https://example.com/profile.jpg")
	private String profilePicture;
}
