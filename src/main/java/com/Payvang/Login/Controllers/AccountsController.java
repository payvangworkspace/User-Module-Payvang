package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Models.LoginRequest;
import com.Payvang.Login.Models.SignupAction;
import com.Payvang.Login.Repositories.UserRepository;
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

		ResponseObject responseObject = userService.createNewUser(userbody);

		if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
			return ResponseEntity.badRequest().body(responseObject);
		}
		return ResponseEntity.ok(responseObject);
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

}
