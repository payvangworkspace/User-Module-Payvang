package com.Payvang.Login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Models.SignupAction;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Util.ErrorType;

@RestController
public class AccountsController {

	     @Autowired
	    private UserService userService;
	     
	     @Autowired
	     private UserRepository userrepository;
	
	@GetMapping("/amit")
	public String message() {
		System.out.println("Profile accessed");
		return "profile accessed";
	}
	
	
	 @PostMapping("/signup") 
	    public ResponseEntity<ResponseObject> createNewUser(@RequestBody SignupAction userbody) {
	    	
	        ResponseObject responseObject = userService.createNewUser(userbody);
	        
	        if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
	            return ResponseEntity.badRequest().body(responseObject);
	        }      
	        return ResponseEntity.ok(responseObject);
	    }
	 
	 @PostMapping("/register") 
	    public ResponseEntity<String> createNewUser(@RequestBody User user) {
	    	
	     //   ResponseObject responseObject = userService.createNewUser(userbody);
		 
		 User users = userrepository.save(user);
	            
	        return ResponseEntity.ok("USer created Successfully");
	    }
}
