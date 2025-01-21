package com.Payvang.Login.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByEmailId(String emailId);

	User getByEmailId(String emailId);

}
