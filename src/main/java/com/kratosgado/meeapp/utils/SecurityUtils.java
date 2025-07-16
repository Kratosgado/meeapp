package com.kratosgado.meeapp.utils;

import com.kratosgado.meeapp.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = new User();
		user.setId(auth.getName());
		return user;
	}
}
