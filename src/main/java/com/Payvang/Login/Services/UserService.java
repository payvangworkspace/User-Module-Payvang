package com.Payvang.Login.Services;


import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.Constants.CrmFieldConstants;
import com.Payvang.Login.Constants.ErrorConstants;
import com.Payvang.Login.Constants.UserType;
import com.Payvang.Login.CustomExceptions.InvalidRequestException;
import com.Payvang.Login.CustomExceptions.SystemException;
import com.Payvang.Login.CustomExceptions.UnauthorizedException;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.User;

import com.Payvang.Login.Models.LoginRequest;
import com.Payvang.Login.Models.SignupAction;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Util.ErrorType;
import com.Payvang.Login.Util.Hasher;
import com.Payvang.Login.Util.JwtUtil;
import com.Payvang.Login.Util.PasswordHasher;
import com.Payvang.Login.Util.SaltFactory;
import com.Payvang.Login.Util.SaltFileManager;
import com.Payvang.Login.Util.TransactionManager;
import com.Payvang.Login.Util.UserStatusType;

@Service
public class UserService {

	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	JwtUtil jwtutil;

	

	public ResponseObject createNewUser(SignupAction userbody) {
		ResponseObject responseObject = null;
		try {
                        
			if (userbody.getUserRoleType().equals(CrmFieldConstants.USER_RESELLER_TYPE.getValue())) {
                         
				responseObject = createUser(getUserInstance(userbody), UserType.RESELLER, "");
			} else {

				responseObject = createUser(getUserInstance(userbody), UserType.MERCHANT, "");
			}

			if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
				return responseObject;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			
			responseObject = new ResponseObject();
			responseObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
			responseObject.setResponseMessage(ErrorType.USER_UNAVAILABLE.getResponseMessage());
		}

		return responseObject;
	}

	private User getUserInstance(SignupAction userbody) {
		User user = new User();
		user.setEmailId(userbody.getEmailId().toLowerCase());
		user.setPassword(userbody.getPassword());
		user.setMobile(userbody.getMobile());
		user.setBusinessName(userbody.getBusinessName());
		if (userbody.getUserRoleType().equals(CrmFieldConstants.USER_RESELLER_TYPE.getValue())) {
		} else {
			user.setIndustryCategory(userbody.getIndustryCategory());
			user.setIndustrySubCategory(userbody.getIndustrySubCategory());
		}
		return user;
	}

	public ResponseObject createUser(User user, UserType userType, String parentappId)
			throws SystemException, jakarta.transaction.SystemException {

		SaltFileManager saltFileManager = new SaltFileManager();

		ResponseObject responseObject = new ResponseObject();
		ResponseObject responseActionObject = new ResponseObject();
		
		Date date = new Date();
		String salt = SaltFactory.generateRandomSalt();

		responseObject = checkuser(user.getEmailId());

		if (ErrorType.USER_AVAILABLE.getResponseCode().equals(responseObject.getResponseCode())) {
             
			if (userType.equals(UserType.RESELLER)) {
				user.setResellerId(TransactionManager.getNewTransactionId());
			}

			user.setUserType(userType);
			user.setUserStatus(UserStatusType.PENDING);
			user.setAppId(getappId());
			user.setAccountValidationKey(TransactionManager.getNewTransactionId());
			user.setEmailValidationFlag(false);
			user.setExpressPayFlag(false);
			user.setRegistrationDate(date);
			
			if (null != user.getPassword()) {
				user.setPassword(Hasher.getHash(user.getPassword().concat(salt)));
			}
			user.setParentappId(parentappId);
			

			userrepository.save(user);
			
			boolean isSaltInserted = saltFileManager.insertSalt(user.getAppId(), salt);

			if (!isSaltInserted) {				
				userrepository.delete(user);
				throw new SystemException(ErrorType.INTERNAL_SYSTEM_ERROR,
						ErrorType.INTERNAL_SYSTEM_ERROR.getResponseMessage());
			}
			responseActionObject.setResponseCode(ErrorType.SUCCESS.getResponseCode());
			responseActionObject.setAccountValidationID(user.getAccountValidationKey());
			responseActionObject.setEmail(user.getEmailId());
		} else {
			responseActionObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
			responseActionObject.setResponseMessage(ErrorType.USER_UNAVAILABLE.getResponseMessage());
		}
		return responseActionObject;
	}

	private String getappId() {
		return TransactionManager.getNewTransactionId();
	}

	public ResponseObject checkuser(String emailId) {
	
         ResponseObject responseObject = new ResponseObject();
		Optional<User> checkedUser = userrepository.findByEmailId(emailId);
		checkedUser.ifPresentOrElse(user -> {
			// If the user is found
			responseObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
			responseObject.setResponseMessage(ErrorType.USER_UNAVAILABLE.getResponseMessage());
		}, () -> {
			// If the user is not found
			responseObject.setResponseCode(ErrorType.USER_AVAILABLE.getResponseCode());
			responseObject.setResponseMessage(ErrorType.USER_AVAILABLE.getResponseMessage());
		});
		return responseObject;
	}

	public ResponseObject loginUser(LoginRequest loginRequest) {
       
		
	            ResponseObject responseObject = new ResponseObject();
	     try {
	    		if(loginRequest.getEmailId() == null || loginRequest.getPassword() == null)
	        	{
	        		throw new InvalidRequestException(ErrorConstants.usernamePasswordNotFound);
	        	}
	        	
	        	String username = loginRequest.getEmailId();
	        	String password = loginRequest.getPassword();
	        	
	        	Optional<User> user = userrepository.findByEmailId(username);
	        	if (user.isEmpty())
	        	{
	        		throw new UnauthorizedException(ErrorConstants.usernameInvalid);
	        	}

                       User users = user.get();
	        	String userStatus = users.getUserStatus().getStatus();
	        	String activeStatus = UserStatusType.ACTIVE.getStatus();
	        	
	    		if (!userStatus.equals(activeStatus))
	    		{
	    			throw new UnauthorizedException(ErrorConstants.userInactive);
	    		}
	    		
	        	
	        	   var userDBPassword = users.getPassword();
	        	  
  	        	password = PasswordHasher.hashPassword(password,users.getAppId());
	            if (password.equals(userDBPassword)) {	            	
	            	 String accessToken = jwtutil.generateToken(users.getEmailId());
	            	 responseObject.setResponseMessage(accessToken);
	            }
	            
        return responseObject;
    }catch(Exception ex) {
    	ex.printStackTrace();
    }
		return responseObject;
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    // Generate OTP    FOR MultiAuthentication System.
//String otp = OtpService.generateOTP();
//// Check if login detail already exists
//LoginDetails loginDetail = loginDetailRepository.findByUsername(username);
//LocalDateTime now = LocalDateTime.now();
//LocalDateTime expiryTime = now.plusMinutes(3); // Time set to 3 minutes
//
//if (loginDetail == null) {
//// Create new login detail entry
//loginDetail = new LoginDetails();
//loginDetail.setUsername(username);
//loginDetail.setOtp(otp);
//loginDetail.setExpiryTime(expiryTime);
//} else {
//// Update existing login detail entry
//loginDetail.setOtp(otp);
//loginDetail.setExpiryTime(expiryTime);
//}
//
//// Save login detail
//loginDetailRepository.save(loginDetail);
//
//// send email for OTP, TODO: make template for it
//
//var emailMessage = this.emailMessage.loginOtpMessage();
//emailService.sendEmail(username, emailMessage.subject, emailMessage.body + otp);
//
//String encryptedUsername = AESEncryptUtility.encrypt(username);
//return new EncryptedKeyGenericResponse(encryptedUsername, SuccessMessage.otpSentToEmail);
//}
//else {
//throw new UnauthorizedException(ErrorConstants.incorrectPassword);
//}

