package com.kratosgado.meeapp.dtos;

public class LoginResponse {

	private String token;
	private Long expiresIn;

	public LoginResponse(String token, Long exp) {
		this.token = token;
		this.expiresIn = exp;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}
}
