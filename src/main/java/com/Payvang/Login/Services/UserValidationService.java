package com.Payvang.Login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.CustomExceptions.UserNotFoundException;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.DataAccess.Models.UserValidation;
import com.Payvang.Login.Repositories.UserValidationRepository;
import com.netflix.discovery.converters.Auto;

@Service
public class UserValidationService {
	
	@Autowired
	private UserValidationRepository userValidationRepository;
	
	public boolean setEmailValid(String email) {
		boolean res=false;
		
		
		try {
	UserValidation userValidation=
			userValidationRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException());
			userValidation.setEmailValidation(true);
			//setting again to db
			userValidationRepository.save(userValidation);
			res=true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	return res;
	
	}
	
	
	public boolean setMobileValid(String email) {
		boolean res=false;
		
		
		try {
	UserValidation userValidation=
			userValidationRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException());
			userValidation.setPhoneValidation(true);
			//setting again to db
			userValidationRepository.save(userValidation);
			res=true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	return res;
	
	}
	
	
	public UserValidation saveUserValidation(String email) {

	UserValidation userValidation=UserValidation.builder().email(email).build();
	UserValidation userValidation2=userValidationRepository.save(userValidation);
	return userValidation2;

}
}
