package com.kratosgado.meeapp.repositories;

import com.kratosgado.meeapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
