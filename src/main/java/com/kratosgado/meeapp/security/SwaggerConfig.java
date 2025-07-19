package com.kratosgado.meeapp.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("MeeApp API")
						.version("1.0")
						.description("API documentation for MeeApp - Group and Event Management System")
						.contact(new Contact()
								.name("MeeApp Team")
								.email("support@meeapp.com")))
				.addSecurityItem(
						new SecurityRequirement().addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes(
						"Bearer Authentication", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								.description("Enter JWT token")));
	}
}
