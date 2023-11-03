package com.debuggeandoideas.app_security.entities;

public class JWTResponse {
	
	private String jwt;

	public JWTResponse(String jwt) {
		this.jwt = jwt;
	}
	

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
