package com.debuggeandoideas.app_security.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.debuggeandoideas.app_security.services.JWTService;
import com.debuggeandoideas.app_security.services.JwtUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTValidationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	private final JwtUserDetailService jwtUserDetailService;

	public JWTValidationFilter(JWTService jwtService, JwtUserDetailService jwtUserDetailService) {
		super();
		this.jwtService = jwtService;
		this.jwtUserDetailService = jwtUserDetailService;
	}
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String AUTHORIZATION_HEADER_BEARER = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final var requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
		String username = null;
		String jwt = null;
		
		if(Objects.nonNull(requestTokenHeader) 
				&& requestTokenHeader.startsWith(AUTHORIZATION_HEADER_BEARER)) {
			jwt = requestTokenHeader.substring(7);
					
			try {
				username = jwtService.getUserNameFromToken(jwt);
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage());
			} catch (ExpiredJwtException e) {
				logger.warn(e.getMessage());
			}
		}
		
		if(Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) ) {
			final var userDetails = this.jwtUserDetailService.loadUserByUsername(username);
			
			if (this.jwtService.validateToken(jwt, userDetails)) {
				var usernameAndPasswordAuthToken = new UsernamePasswordAuthenticationToken(
						userDetails, null,userDetails.getAuthorities());
				
				usernameAndPasswordAuthToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordAuthToken);
			}
		}
		
		filterChain.doFilter(request, response);

	}

}
