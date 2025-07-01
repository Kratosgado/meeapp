package com.kratosgado.meeapp.security;

import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	private static final String SECRET_KEY = "hdfjdklfj-kldfjdk8790kdjf080-j";

	public String extractUsername(String token) { return extractClaim() }

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	}
}
