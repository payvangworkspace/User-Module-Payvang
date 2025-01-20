package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Payvang.Login.Models.MobileRequest;
import com.Payvang.Login.Models.OtpRequest;
import com.Payvang.Login.Services.OtpService;

@RestController
@RequestMapping("/auth/otp")
public class OtpController {

	@Autowired
	private OtpService otpService;

	@PostMapping("/generate")
	public ResponseEntity<String> generateOtp(@RequestBody MobileRequest mobileRequest) {
		try {
			boolean isOtpSent = otpService.sendOtp(mobileRequest.getPhone());
			if (isOtpSent) {
				return ResponseEntity.ok("OTP generated and saved successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate and save OTP.");
			}
		} catch (Exception e) {
			// Log and handle unexpected errors
			System.err.println("Error in OTP generation: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred while generating the OTP.");
		}
	}

	@PostMapping("/verify")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest otpRequest) {
		try {
			boolean isValid = otpService.validateOtp(otpRequest.getOtp());
			if (isValid) {
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
}
