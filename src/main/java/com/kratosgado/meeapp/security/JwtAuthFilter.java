package com.kratosgado.meeapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final HandlerExceptionResolver hResolver;
	private final JwtService jwtService;
	private final UserDetailsService uService;
	private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

	public JwtAuthFilter(
			JwtService jwtService,
			UserDetailsService uService,
			HandlerExceptionResolver handlerExceptionResolver) {
		this.jwtService = jwtService;
		this.hResolver = handlerExceptionResolver;
		this.uService = uService;
	}

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest req,
			@NonNull HttpServletResponse res,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = req.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(req, res);
			return;
		}

		try {
			final String jwt = authHeader.substring(7);
			final String userId = jwtService.extractUsername(jwt);
			// final String userId = jwtService.extractUserId(jwt);
			logger.info("authenticating user {}", userId);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (userId != null && auth == null) {
				UserDetails userDetails = this.uService.loadUserByUsername(userId);

				if (jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			filterChain.doFilter(req, res);
		} catch (Exception e) {
			hResolver.resolveException(req, res, null, e);
		}
	}
}
