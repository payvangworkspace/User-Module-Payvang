package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Models.MobileRequest;
import com.Payvang.Login.Models.OtpRequest;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Services.OtpService;
import com.Payvang.Login.Services.UserValidationService;
import com.Payvang.Login.Util.AESEncryptUtility;

@RestController
@RequestMapping("/auth/otp")
public class OtpController {

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private UserValidationService userValidationService;

	@PostMapping("/verify")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest otpRequest) {
		try {
			boolean isValid = otpService.validateOtp(otpRequest.getOtp(), otpRequest.getRecipient());
			if (isValid) {
				//set the email validation as true
				boolean res=userValidationService.setMobileValid(otpRequest.getRecipient());
				if(res) {
					System.out.println("Mobile Validated and set successfully..");
				}
				return ResponseEntity.ok("OTP verification successful.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
			}
		} catch (Exception e) {
			// Log and handle unexpected errors
			System.err.println("Error in OTP verification: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred while verifying the OTP.");
		}
	}

	@PostMapping("/email")
	public ResponseEntity<?> validateEmail(@RequestParam("id") String emailId) {
		ResponseObject response = new ResponseObject();
		try {

			String decryptedEmail = AESEncryptUtility.decrypt(emailId);

			User user = userrepository.findByEmailId(decryptedEmail)
					.orElseThrow(() -> new RuntimeException("User not found"));

			String maskedMobileNumber = otpService.maskMobileNumber(user.getMobile());

			boolean otpSent = otpService.sendOtp(decryptedEmail);
			if (otpSent) {
				response.setResponseMessage("Email validated and OTP sent successfully to " + maskedMobileNumber);
				//set the email validation as true
				boolean res=userValidationService.setEmailValid(decryptedEmail);
				if(res) {
					System.out.println("Email Validated and set successfully..");
				}
				response.setResponseCode("SUCCESS");
			} else {
				response.setResponseMessage("Failed to send OTP. Please try again.");
				response.setResponseCode("FAILURE");
			}

			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {

			response.setResponseMessage("Invalid email or user not found.");
			response.setResponseCode("UNAUTHORIZED");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {

			response.setResponseMessage("An unexpected error occurred.");
			response.setResponseCode("ERROR");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

//		@PostMapping("/generate")
//		public ResponseEntity<String> generateOtp(@RequestBody MobileRequest mobileRequest) {
//			try {
//				boolean isOtpSent = otpService.sendOtp(mobileRequest.getPhone());
//				if (isOtpSent) {
//					return ResponseEntity.ok("OTP generated and saved successfully.");
//				} else {
//					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate and save OTP.");
//				}
//			} catch (Exception e) {
//				// Log and handle unexpected errors
//				System.err.println("Error in OTP generation: " + e.getMessage());
//				e.printStackTrace();
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//						.body("An unexpected error occurred while generating the OTP.");
//			}
//		}
	
	
	
	
	
	
	
	
}
