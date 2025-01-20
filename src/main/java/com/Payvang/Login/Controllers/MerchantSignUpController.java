package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Payvang.Login.DataAccess.Models.MerchantSignup;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.Services.UserService;
import com.Payvang.Login.Util.ErrorType;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class MerchantSignUpController {
 
	@Autowired
	UserService userService;
	
	
	@PostMapping("/merchantSignUp")
	public ResponseEntity<ResponseObject> createNewUser(@RequestBody MerchantSignup userbody) {
	    try {
	        ResponseObject responseObject = userService.createMerchant(userbody);

	        if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
	            return ResponseEntity.badRequest().body(responseObject);
	        }
	        return ResponseEntity.ok(responseObject);
	    } catch (Exception e) {
	        // Log the exception for debugging purposes (optional)
	        // logger.error("Error during merchant sign up", e);

	        ResponseObject errorResponse = new ResponseObject();
	        errorResponse.setResponseCode(ErrorType.DATABASE_ERROR.getResponseCode());
	        errorResponse.setResponseMessage("An unexpected error occurred during merchant sign up.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
}