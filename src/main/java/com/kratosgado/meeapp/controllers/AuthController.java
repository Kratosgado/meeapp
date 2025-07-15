package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.LoginDto;
import com.kratosgado.meeapp.dtos.LoginResponse;
import com.kratosgado.meeapp.dtos.RegisterUserDto;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.security.JwtService;
import com.kratosgado.meeapp.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
	public final JwtService jwtService;
	private final AuthService authService;

	public AuthController(JwtService jwtService, AuthService authService) {
		this.jwtService = jwtService;
		this.authService = authService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody RegisterUserDto body) {
		User regUser = authService.signup(body);
		return ResponseEntity.ok(regUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginDto body) {
		User authUser = authService.login(body);
		String token = jwtService.generateToken(authUser);
		LoginResponse res = new LoginResponse(token, jwtService.getExpirationTime());

		return ResponseEntity.ok(res);
	}
}
