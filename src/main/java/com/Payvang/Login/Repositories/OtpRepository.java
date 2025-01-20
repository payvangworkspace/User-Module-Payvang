package com.Payvang.Login.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.Otp;

@Repository
public interface OtpRepository extends CrudRepository<Otp, Integer> {
	Optional<Otp> findByOtpAndRecipient(String otp,String recipient);

	Otp findByRecipient(String phone);
}
