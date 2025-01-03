package com.Payvang.Login.Util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Repositories.UserRepository;

//@Component
public class CheckExistingUser {
	
//	@Autowired
//	UserRepository userrepository;
//	
//	
//
//	
//	private ResponseObject responseObject= new ResponseObject();
//	
//     public ResponseObject checkuser(String emailId) {
//    	 System.out.println("Checking " +emailId);
//    	 
//    	
//		Optional<User>	checkedUser = userrepository.findByEmailId(emailId);
//			if (null != checkedUser){
//				responseObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
//				responseObject.setResponseMessage(ErrorType.USER_UNAVAILABLE.getResponseMessage());
//			 } else {
//				
//				responseObject.setResponseCode(ErrorType.USER_AVAILABLE.getResponseCode());
//				responseObject.setResponseMessage(ErrorType.USER_AVAILABLE.getResponseMessage());
//				}
//			return responseObject;
//		}
//     
//     public User getUserByEmail(String emailId) {
//    	    return userrepository.findByEmailId(emailId)
//    	        .orElseThrow(() -> new RuntimeException("User not found"));
//     }
}
