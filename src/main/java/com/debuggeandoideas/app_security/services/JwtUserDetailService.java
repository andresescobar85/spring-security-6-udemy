package com.debuggeandoideas.app_security.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.debuggeandoideas.app_security.repositories.CustomerRepository;

@Service
public class JwtUserDetailService implements UserDetailsService {
	
	private final CustomerRepository customerRepository;
	
	public JwtUserDetailService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.customerRepository.findByEmail(username)
				.map(customer->{
					final var authorities = customer.getRoles()
							.stream()
							.map(role-> new SimpleGrantedAuthority(role.getName()))
							.toList();
			return new User(customer.getEmail(),customer.getPassword(),authorities);
		}).orElseThrow(()-> new UsernameNotFoundException("User not exist"));
		
	}

}
