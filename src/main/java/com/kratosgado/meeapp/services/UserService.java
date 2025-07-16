package com.kratosgado.meeapp.services;

import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.repositories.UserRepo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepo userRepo;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public User getMe() {
    String id = SecurityContextHolder.getContext().getAuthentication().getName();
    logger.info("Fetching user details for id: {}", id);
    return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public List<User> getAllUsers() {
    logger.error("Fetching user details for id: {}");
    return userRepo.findAll();
  }

  public User getUserById(String id) {
    return userRepo.findById(id).orElse(null);
  }

  public User getUserByEmail(String email) {
    return userRepo.findByEmail(email).orElseThrow();
  }

  public User saveUser(User user) {
    return userRepo.save(user);
  }

  public void deleteUser(String id) {
    userRepo.deleteById(id);
  }
}
