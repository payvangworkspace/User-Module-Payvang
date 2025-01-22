package com.Payvang.Login.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.UserValidation;

@Repository
public interface UserValidationRepository extends CrudRepository<UserValidation, Integer> {
	Optional<UserValidation>findByEmail(String email);
	

}
