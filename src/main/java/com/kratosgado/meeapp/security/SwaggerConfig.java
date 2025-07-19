package com.kratosgado.meeapp.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenApi() {
    Server server = new Server();
    server.setUrl("http://localhost:9000");
    server.setDescription("Development");

    Contact myContact = new Contact().name("MeeApp Team").email("support@meeapp.com");
    Info information =
        new Info()
            .title("MeeApp API")
            .version("1.0")
            .description("API documentation for MeeApp - Group and " + "Event Management System")
            .contact(myContact);
    return new OpenAPI()
        .info(information)
        .servers(List.of(server))
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(
            new Components()
                .addSecuritySchemes(
                    "Bearer Authentication",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Enter JWT token")));
  }
}
