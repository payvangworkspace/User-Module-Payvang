package com.Payvang.Login.Services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
 
import com.Payvang.Login.DataAccess.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.Payvang.Login.CustomExceptions.InvalidRequestException;
import com.Payvang.Login.DataAccess.Models.EmailRequest;
import com.Payvang.Login.DataAccess.Models.EmailResponse;
import com.Payvang.Login.DataAccess.Models.Otp;
import com.Payvang.Login.External.Services.EmailService;
import com.Payvang.Login.Repositories.OtpRepository;
import com.Payvang.Login.Repositories.UserRepository;
 
@Service
public class OtpService {
 
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserRepository userRepository;

   @Autowired
   private EmailService emailService;

 
    public boolean sendOtp(String phone) {
        try {
            // Generating six-digit OTP
            String otp = generateOTP();
            System.out.println("Generated OTP: " + otp);
 
            // Setting trigger and expiry times
            LocalDateTime triggerTime = LocalDateTime.now();
            LocalDateTime expiryTime = triggerTime.plus(3, ChronoUnit.MINUTES);
 
//            System.out.println("OTP Trigger Time: " + triggerTime);
//            System.out.println("OTP Expiry Time: " + expiryTime);

            //for a time being sending otp on the mail

            //Fetching the user for the database
           User user= userRepository.findByEmailId(phone).orElseThrow(()->new InvalidRequestException("User not found"));

            EmailRequest emailRequest=EmailRequest.builder().message("Your OTP for mobile number verification "+otp)
            		.subject("Mobile number Verification").to(user.getEmailId())
            		.build();
            //Sending mail for now
            EmailResponse emailResponse=emailService.sendEmail(emailRequest);
           //response of mail
            System.out.println("Mail Response of OTP "+emailResponse);
              
            Otp otpdb = otpRepository.findByRecipient(phone);
            
            if(otpdb == null) {
            	 Otp otpEntity = Otp.builder()
                         .otp(otp)
                         .triggerTime(java.sql.Timestamp.valueOf(triggerTime))
                         .expiryTime(java.sql.Timestamp.valueOf(expiryTime))
                         .recipient(phone)
                         .build();
      
                 otpRepository.save(otpEntity); // Save the OTP entity
            }
            // Save OTP details to the database
            else {
            	otpdb.setOtp(otp);
            	otpdb.setExpiryTime(java.sql.Timestamp.valueOf(expiryTime));
            	otpdb.setTriggerTime(java.sql.Timestamp.valueOf(triggerTime));
            	otpRepository.save(otpdb);
            	
            }


 
            return true; // Return true after successfully saving the OTP
        } catch (Exception e) {
            // Log the error and return false to indicate failure
            System.err.println("Error occurred while saving OTP to the database: " + e.getMessage());
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
    }
 
    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Generate a number between 100000 and 999999
        return String.valueOf(otp);
    }

    public boolean validateOtp(String inputOtp,String recipient) {
        try {
        	boolean response=false;
            // Fetch OTP entity from the database
            Otp otpEntity = otpRepository.findByOtpAndRecipient(inputOtp,recipient)
                    .orElseThrow(() -> new InvalidRequestException("Invalid OTP."));
 
            // Check if the OTP has expired
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime expiryTime = otpEntity.getExpiryTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
 
            if (currentTime.isAfter(expiryTime)) {
                throw new InvalidRequestException("OTP has expired.");
            }
            response=true;
 
            // OTP is valid
            return response;
        } catch (InvalidRequestException e) {
            // Log specific validation errors
            System.err.println("Validation Error: " + e.getMessage());
            throw e; // Re-throw to be handled at a higher level
        } catch (Exception e) {
            // Log unexpected errors
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("An error occurred while validating the OTP.");
        }
    }
 
    
    public String maskMobileNumber(String mobileNumber) {
        if (mobileNumber == null || mobileNumber.length() < 10) {
            throw new IllegalArgumentException("Invalid mobile number");
        }
        String masked = "******" + mobileNumber.substring(6);
        return masked;
    }
    
}