package com.debuggeandoideas.app_security.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.debuggeandoideas.app_security.entities.CustomerEntity;

public interface CustomerRepository extends CrudRepository<CustomerEntity, BigInteger> {
	
	Optional<CustomerEntity>findByEmail(String email);
}
