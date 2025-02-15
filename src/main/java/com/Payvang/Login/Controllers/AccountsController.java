package com.Payvang.Login.Controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Set;
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

import com.Payvang.Login.Constants.Constants;
import com.Payvang.Login.Constants.CrmFieldConstants;
import com.Payvang.Login.Constants.UserType;
import com.Payvang.Login.DataAccess.Models.EmailVerifyRequest;
import com.Payvang.Login.DataAccess.Models.LoginHistory;
import com.Payvang.Login.DataAccess.Models.Permissions;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.Roles;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Models.ChangePasswordRequest;
import com.Payvang.Login.Models.LoginRequest;
import com.Payvang.Login.Models.SignupAction;
import com.Payvang.Login.Services.LoginAuthenticator;
import com.Payvang.Login.Services.UserService;
import com.Payvang.Login.Util.ErrorType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("api/auth/")
public class AccountsController {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private LoginAuthenticator loginauthenticator;
	
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
	
//	 @PostMapping("/login")
//	    public ResponseEntity<ResponseObject> loginUser(@RequestBody LoginRequest loginRequest) {
//	        // Login request contains email and password
//	        ResponseObject responseObject = userService.loginUser(loginRequest);
//
//	        if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
//	            return ResponseEntity.badRequest().body(responseObject);
//	        }
//	        return ResponseEntity.ok(responseObject);
//	    }


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


	@PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUsers(@RequestBody LoginRequest loginRequest,HttpServletRequest request,HttpSession session) {
		
                            String emailId = loginRequest.getEmailId();
                            String password = loginRequest.getPassword();
                            ResponseObject responseObject = new ResponseObject();
             try {           
                             String ipAddress = request.getHeader("X-Forwarded-For");
                         if (ipAddress == null) {
                             ipAddress = request.getRemoteAddr();
                             }
   
            responseObject = loginauthenticator.authenticate(emailId, password,request.getHeader(CrmFieldConstants.USER_AGENT.getValue()),ipAddress);

            if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObject);
            }
			
            LoginHistory loginHistory = userService.findLastLoginByUser(emailId);
         
                      User user = loginauthenticator.getUser();
            session.setAttribute(Constants.USER.getValue(), user);
            session.setAttribute(Constants.LAST_LOGIN.getValue(), loginHistory);
            session.setAttribute(Constants.CUSTOM_TOKEN.getValue(), responseObject.getResponseMessage());

         if (user.getUserType().equals(UserType.SUBUSER) || user.getUserType().equals(UserType.SUBACQUIRER) || user.getUserType().equals(UserType.SUBADMIN)) {
                
                                Set<Roles> roles = user.getRoles();
                                Set<Permissions> permissions = roles.iterator().next().getPermissions();
                if (!permissions.isEmpty()) {
                    String permissionString = permissions.stream()
                        .map(p -> p.getPermissionType().getPermission())
                        .collect(Collectors.joining("-"));
                    session.setAttribute(Constants.USER_PERMISSION.getValue(), permissionString);
                }
            }
         
            String redirectUrl = user.getLastActionName();
            if (redirectUrl != null) {
            	responseObject.setResponseMessage("User Login Successfully");
                return ResponseEntity.ok(responseObject);
            }

            return ResponseEntity.ok(responseObject);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
        }
    }


}
