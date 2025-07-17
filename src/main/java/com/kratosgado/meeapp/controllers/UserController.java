package com.kratosgado.meeapp.controllers;

import com.kratosgado.meeapp.dtos.CustomResponse;
import com.kratosgado.meeapp.models.User;
import com.kratosgado.meeapp.services.UserService;
import com.kratosgado.meeapp.utils.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@Tag(name = "Users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @Operation(summary = "Get current authenticated user")
  @GetMapping("/me")
  public ResponseEntity<User> getMe() {
    return ResponseEntity.ok(userService.getMe());
    // return ResponseEntity.ok(userService.getMe());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomResponse> getUserById(@PathVariable String id) {
    User user = userService.getUserById(id);
    if (user == null) {
      throw new ApiException("User not found", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(
        new CustomResponse(user, "User retrieved successfully", HttpStatus.OK));
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
  }
}
