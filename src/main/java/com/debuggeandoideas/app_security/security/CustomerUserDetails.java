package com.debuggeandoideas.app_security.security;


import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.debuggeandoideas.app_security.repositories.CustomerRepository;


@Service
@Transactional
public class CustomerUserDetails implements UserDetailsService {
	
	private final CustomerRepository customerRepository;
	
	public CustomerUserDetails(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.customerRepository.findByEmail(username)
				.map(customer ->{
					var authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
					return new User(customer.getEmail(),customer.getPassword(),authorities);
				}).orElseThrow(()-> new UsernameNotFoundException("User not found"));
	}

}
