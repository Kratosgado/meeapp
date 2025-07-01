package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.dtos.LoginDto;
import com.kratosgado.meeapp.dtos.RegisterUserDto;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.repositories.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;

	public AuthService(
			UserRepo userRepo, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.authManager = authManager;
	}

	public User signup(RegisterUserDto input) {
		input.setPassword(passwordEncoder.encode(input.getPassword()));
		User user = new User(input);
		return userRepo.save(user);
	}

	public User login(LoginDto input) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
		return userRepo.findByEmail(input.getEmail()).orElseThrow();
	}
}
