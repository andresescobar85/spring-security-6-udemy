package com.debuggeandoideas.app_security.security;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.debuggeandoideas.app_security.repositories.CustomerRepository;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;
	
	public MyAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		super();
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final var username = authentication.getName();
		final var pwd =authentication.getCredentials().toString();
		
		final var customerFromDb = this.customerRepository.findByEmail(username);
		final var customer = customerFromDb.orElseThrow(()-> new BadCredentialsException("Invalid Credentials"));
		final var customerPwd = customer.getPassword();
		
		if(passwordEncoder.matches(pwd,customerPwd)) {
			final var authorities = Collections.singleton(new SimpleGrantedAuthority(customer.getRole()));
			return new UsernamePasswordAuthenticationToken(username, pwd,authorities);
		}else {
			throw new BadCredentialsException("Invalid Credentials");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
