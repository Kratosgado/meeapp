package com.kratosgado.meeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Login request data")
public class LoginDto {
	@Schema(description = "User email", example = "user@example.com", required = true)
	private String email;

	@Schema(description = "User password", example = "password123", required = true)
	private String password;
}
