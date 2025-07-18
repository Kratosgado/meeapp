package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.LoginDto;
import com.kratosgado.meeapp.dtos.LoginResponse;
import com.kratosgado.meeapp.dtos.RegisterUserDto;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.security.JwtService;
import com.kratosgado.meeapp.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {
  private final JwtService jwtService;
  private final AuthService authService;

  public AuthController(JwtService jwtService, AuthService authService) {
    this.jwtService = jwtService;
    this.authService = authService;
  }

  @Operation(summary = "Register a new user", description = "Creates a new user account with the provided information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data or email already exists")
  })
  @PostMapping("/signup")
  public ResponseEntity<User> register(@RequestBody RegisterUserDto body) {
    User regUser = authService.signup(body);
    return ResponseEntity.ok(regUser);
  }

  @Operation(summary = "Login user", description = "Authenticates a user with email and password, returns a JWT token for subsequent API calls")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
      @ApiResponse(responseCode = "401", description = "Invalid credentials")
  })
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginDto body) {
    User authUser = authService.login(body);
    String token = jwtService.generateToken(authUser);
    LoginResponse res = new LoginResponse(token, jwtService.getExpirationTime());

    return ResponseEntity.ok(res);
  }
}
