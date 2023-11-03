package com.debuggeandoideas.app_security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.debuggeandoideas.app_security.entities.JWTRequest;
import com.debuggeandoideas.app_security.entities.JWTResponse;
import com.debuggeandoideas.app_security.services.JWTService;
import com.debuggeandoideas.app_security.services.JwtUserDetailService;

@RestController
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUserDetailService jwtUserDetailService;
	private final JWTService jwtService;
	
	
	public AuthController(AuthenticationManager authenticationManager, JwtUserDetailService jwtUserDetailService,
			JWTService jwtService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUserDetailService = jwtUserDetailService;
		this.jwtService = jwtService;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> postToken(@RequestBody JWTRequest request){
		this.autenticate(request);
		final var userDetails=this.jwtUserDetailService.loadUserByUsername(request.getUsername());
		
		final String token = this.jwtService.generateToken(userDetails);
		
		return ResponseEntity.ok(new JWTResponse(token));
		
	}
	
	private void autenticate(JWTRequest request) {
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException | DisabledException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
