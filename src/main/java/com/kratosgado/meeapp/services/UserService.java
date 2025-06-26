package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.repositories.UserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepo userRepo;

	@Autowired
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User getUserById(Long id) {
		return userRepo.findById(id).orElse(null);
	}

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public User saveUser(User user) {
		return userRepo.save(user);
	}

	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}
}
