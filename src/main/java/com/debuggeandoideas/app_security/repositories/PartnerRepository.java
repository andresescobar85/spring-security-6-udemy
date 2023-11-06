package com.debuggeandoideas.app_security.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.debuggeandoideas.app_security.entities.PartnerEntity;

public interface PartnerRepository extends CrudRepository<PartnerEntity, BigInteger> {

	Optional<PartnerEntity> findByClientId(String clientId);

}
