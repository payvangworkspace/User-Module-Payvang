package com.Payvang.Login.Controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Payvang.Login.DataAccess.Models.EmailVerifyRequest;
import com.Payvang.Login.DataAccess.Models.LoginHistory;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.Models.ChangePasswordRequest;
import com.Payvang.Login.Models.LoginRequest;
import com.Payvang.Login.Models.SignupAction;
import com.Payvang.Login.Services.UserService;
import com.Payvang.Login.Util.ErrorType;

@CrossOrigin
@RestController
@RequestMapping("api/auth/")
public class AccountsController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/merchant")
	public ResponseEntity<ResponseObject> createNewUser(@RequestBody SignupAction userbody) {
		  try {
		ResponseObject responseObject = userService.createNewUser(userbody);

		if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
			return ResponseEntity.badRequest().body(responseObject);
		}
		return ResponseEntity.ok(responseObject);
		  } catch (Exception e) {
		       
                e.printStackTrace();
		        ResponseObject errorResponse = new ResponseObject();
		        errorResponse.setResponseCode(ErrorType.DATABASE_ERROR.getResponseCode());
		        errorResponse.setResponseMessage("An unexpected error occurred during merchant sign up.");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		    }
	}
	
	 @PostMapping("/login")
	    public ResponseEntity<ResponseObject> loginUser(@RequestBody LoginRequest loginRequest) {
	        // Login request contains email and password
	        ResponseObject responseObject = userService.loginUser(loginRequest);

	        if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
	            return ResponseEntity.badRequest().body(responseObject);
	        }
	        return ResponseEntity.ok(responseObject);
	    }


	@PostMapping("/random-password")
	public ResponseEntity<?> generateRandomPassword(@RequestBody EmailVerifyRequest emailVerifyRequest) {
		boolean res = userService.CreateDummyPassword(emailVerifyRequest.getEmail());
		if (res) {
			return ResponseEntity.status(HttpStatus.CREATED).body("User has been created with the dummy password");

		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
	}

	@PostMapping("/changepassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {

		var response = userService.executeChangePassword(changePasswordRequest);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/{emailId}")
    public ResponseEntity<List<LoginHistory>> getLoginHistory(@PathVariable ("emailId")String emailId) {
        List<LoginHistory> loginHistoryList = userService.getLoginHistoryByUserId(emailId);

        if (loginHistoryList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }
        return ResponseEntity.ok(loginHistoryList);
    }



}
