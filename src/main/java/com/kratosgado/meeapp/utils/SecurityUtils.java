package com.kratosgado.meeapp.utils;

import com.kratosgado.meeapp.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
	private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// User user = new User();
		return (User) auth.getPrincipal();
	}
}
