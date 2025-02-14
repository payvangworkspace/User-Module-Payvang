package com.Payvang.Login.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.Constants.CrmFieldConstants;
import com.Payvang.Login.Constants.ErrorConstants;
import com.Payvang.Login.Constants.UserType;
import com.Payvang.Login.CustomExceptions.InvalidRequestException;
import com.Payvang.Login.CustomExceptions.SystemException;
import com.Payvang.Login.CustomExceptions.UnauthorizedException;
import com.Payvang.Login.DataAccess.Models.AuditTrail;
import com.Payvang.Login.DataAccess.Models.EmailRequest;
import com.Payvang.Login.DataAccess.Models.EmailResponse;
import com.Payvang.Login.DataAccess.Models.LoginHistory;
import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.DataAccess.Models.MerchantSignup;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.External.Services.EmailService;
import com.Payvang.Login.Models.ChangePasswordRequest;
import com.Payvang.Login.Models.ChangePasswordResponse;
import com.Payvang.Login.Models.LoginRequest;
import com.Payvang.Login.Models.SignupAction;
import com.Payvang.Login.Models.UserRecords;
import com.Payvang.Login.Repositories.LoginHistoryRepository;
import com.Payvang.Login.Repositories.MerchantRepository;
import com.Payvang.Login.Repositories.UserRecordsRepository;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Util.AESEncryptUtility;
import com.Payvang.Login.Util.ErrorType;
import com.Payvang.Login.Util.Hasher;
import com.Payvang.Login.Util.JwtUtil;
import com.Payvang.Login.Util.PasswordHasher;
import com.Payvang.Login.Util.SaltFactory;
import com.Payvang.Login.Util.SaltFileManager;
import com.Payvang.Login.Util.TransactionManager;
import com.Payvang.Login.Util.UserStatusType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;



@Service
public class UserService {

	@Autowired
	private UserRepository userrepository;

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MerchantRepository merchantrepository;

	@Value("${password.user.dummy}")
	private String dummyPassword;

	@Autowired
	private ObjectMapper objectMapper;

	
	@Autowired
	private UserValidationService userValidationService;
	

	@Autowired
	private UserRecordsRepository userRecordsRepository;


	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private LoginHistoryRepository loginhistoryrepository;


	@Autowired
	JwtUtil jwtutil;

	public ResponseObject createNewUser(SignupAction userbody) {
		ResponseObject responseObject = null;
		try {
			User user = userrepository.getByEmailId(userbody.getEmailId());
			if (user != null) {
				String usertype = user.getUserType().name();

				if (usertype.equals(CrmFieldConstants.USER_RESELLER_TYPE.getValue())) {

					responseObject = createUser(getUserInstance(userbody), UserType.RESELLER, "");

				} else {

					responseObject = createUser(getUserInstance(userbody), UserType.MERCHANT, "");
				}

				if (!ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
					return responseObject;
				}

			}else {
				responseObject = new ResponseObject();
				responseObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
				responseObject.setResponseMessage(ErrorType.USER_UNAVAILABLE.getResponseMessage());
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
		User user = userrepository.getByEmailId(userbody.getEmailId());
		
		user.setEmailId(userbody.getEmailId().toLowerCase());
		user.setPassword(userbody.getPassword());
		//user.setMobile(userbody.getMobile());
		//user.setBusinessName(userbody.getBusinessName());
//		if (userbody.getUserRoleType().equals(CrmFieldConstants.USER_RESELLER_TYPE.getValue())) {
//		} else {
//			user.setIndustryCategory(userbody.getIndustryCategory());
//			user.setIndustrySubCategory(userbody.getIndustrySubCategory());
//		}
		return user;
	}

	public ResponseObject createUser(User user, UserType userType, String parentappId)
			throws SystemException, jakarta.transaction.SystemException {

		SaltFileManager saltFileManager = new SaltFileManager();

//		ResponseObject responseObject = new ResponseObject();
		ResponseObject responseActionObject = new ResponseObject();

		Date date = new Date();
		String salt = SaltFactory.generateRandomSalt();

//		responseObject = checkuser(user.getEmailId());

//		if (ErrorType.USER_AVAILABLE.getResponseCode().equals(responseObject.getResponseCode())) {

			if (userType.equals(UserType.RESELLER)) {
				user.setResellerId(TransactionManager.getNewTransactionId());
			}

			//user.setUserType(userType);
			user.setUserStatus(UserStatusType.PENDING);
			user.setAppId(getappId());
			user.setAccountValidationKey(TransactionManager.getNewTransactionId());
			user.setEmailValidationFlag(false);
			//user.setExpressPayFlag(false);
			user.setRegistrationDate(date);

			if (null != user.getPassword()) {
				user.setPassword(Hasher.getHash(user.getPassword().concat(salt)));
			}
			user.setParentappId(parentappId);

			userrepository.save(user);
			
			 if (user.getUserType() == UserType.MERCHANT) {
			        Merchant merchant = new Merchant();
			        merchant.setAppId(user.getAppId());
			        merchant.setBusinessName(user.getBusinessName());
			        merchant.setContactEmail(user.getEmailId());
			        merchant.setUser(user); // Set the relationship

			        merchantrepository.save(merchant);
			    }

			boolean isSaltInserted = saltFileManager.insertSalt(user.getAppId(), salt);

			if (!isSaltInserted) {
				userrepository.delete(user);
				throw new SystemException(ErrorType.INTERNAL_SYSTEM_ERROR,
						ErrorType.INTERNAL_SYSTEM_ERROR.getResponseMessage());
			}
			responseActionObject.setResponseCode(ErrorType.SUCCESS.getResponseCode());
			responseActionObject.setAccountValidationID(user.getAccountValidationKey());
			responseActionObject.setEmail(user.getEmailId());
//		} else {
//			responseActionObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
//			responseActionObject.setResponseMessage(ErrorType.USER_UNAVAILABLE.getResponseMessage());
//		}
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
			if (loginRequest.getEmailId() == null || loginRequest.getPassword() == null) {
				throw new InvalidRequestException(ErrorConstants.usernamePasswordNotFound);
			}

			String username = loginRequest.getEmailId();
			String password = loginRequest.getPassword();

			Optional<User> user = userrepository.findByEmailId(username);
			if (user.isEmpty()) {
				throw new UnauthorizedException(ErrorConstants.usernameInvalid);
			}

			User users = user.get();
			String userStatus = users.getUserStatus().getStatus();
			String activeStatus = UserStatusType.ACTIVE.getStatus();

			if (!userStatus.equals(activeStatus)) {
				throw new UnauthorizedException(ErrorConstants.userInactive);
			}

			var userDBPassword = users.getPassword();

			password = PasswordHasher.hashPassword(password, users.getAppId());
			if (password.equals(userDBPassword)) {
				String accessToken = jwtutil.generateToken(users.getEmailId());
				responseObject.setResponseMessage(accessToken);
				responseObject.setResponseCode(ErrorType.SUCCESS.getResponseCode());
			}

			return responseObject;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseObject;
	}

	public User findUserProfile(String emailid) {
		Optional<User> user = userrepository.findByEmailId(emailid);
		if (user.isPresent()) {
			User userdata = user.get();
			System.out.println("here is user data " + userdata);
			return userdata;
		} else {
			throw new InvalidRequestException(ErrorConstants.usernamePasswordNotFound);
		}

	}

	public ResponseObject createMerchant(MerchantSignup userbody) {

		ResponseObject responseObject = new ResponseObject();
		try {
			User user = new User();
			String emailId = userbody.getEmailId();
			if((userbody.getEmailId() == null) || (userbody.getUserRoleType()==null)) {
				responseObject.setResponseMessage("EmailId or RoleType is Empty");
				return responseObject;
			}
			User userdb = userrepository.getByEmailId(emailId);
			if (userdb != null) {
				responseObject.setResponseMessage("User Already Exist");
				return responseObject;
			}
			if(userbody.getUserRoleType().equals(CrmFieldConstants.USER_RESELLER_TYPE.getValue())) {
				user.setUserType(UserType.RESELLER);
			}else {
				user.setUserType(UserType.MERCHANT);
			}
			user.setEmailId(emailId);
			user.setBusinessName(userbody.getBusinessName());
			user.setMobile(userbody.getMobile());
			user.setIndustryCategory(userbody.getIndustryCategory());
			user.setIndustrySubCategory(userbody.getIndustrySubCategory());
			user.setFirstName(userbody.getFirstName());
			user.setLastName(userbody.getLastName());
			User userdata = userrepository.save(user);
			
				responseObject.setResponseCode(ErrorType.SUCCESS.getResponseCode());
				responseObject.setResponseMessage("Merchant created Successfully");
				
			//Adding the audit trail for it
		AuditTrail auditTrail = new AuditTrail();
auditTrail.setEntityName("User Entity");
			auditTrail.setAction("MERCHANT SIGNUP ACTION");
			auditTrail.setPerformedByUser("ADMIN");
			auditTrail.setPerformedAt(LocalDateTime.now());
			auditTrail.setUpdatedat(LocalDateTime.now());
			auditTrail.setOldValue("NA"); // Save old value as JSON
			auditTrail.setNewValue(toJson(user)); // Save new value as JSON
			auditTrail.setIpAddress("127.0.0.1");
			auditTrail.setComments("New Merchant has been created by ADMIN");

			// saving to db

			auditTrailService.saveAuditTrail(auditTrail);


				//setting the user validation entry to db
				userValidationService.saveUserValidation(emailId);
				
				
				
				String encryptedemail = AESEncryptUtility.encrypt(emailId);
                   
				// Calling External Service to send email--Nitesh
				EmailRequest emailRequest = EmailRequest.builder().to(userbody.getEmailId())
						.message("Congratulation Merchant, your account has been registered successfully. \n Please Verify your Mobile Number with the link \n http://localhost:3000/verify?id="+encryptedemail)
						.subject("Congratulations your account has been registered.").build();
				EmailResponse emailResponse = emailService.sendEmail(emailRequest);

				logger.info("Email has been send successfully.");
			
			return responseObject;
		} catch (Exception exception) {
			exception.printStackTrace();

			responseObject = new ResponseObject();
			responseObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
			responseObject.setResponseMessage(ErrorType.USER_UNAVAILABLE.getResponseMessage());
		}

		return responseObject;
	}

	// Creating the dummy password and validating fields
	public boolean CreateDummyPassword(String email) {
		boolean res = false;
		// setting the validation as true for mobile

		try {
			boolean response = userValidationService.setEmailValid(email);
			if (response) {
				// setting the password

				SignupAction signup = SignupAction.builder().emailId(email).password(dummyPassword).build();
				createNewUser(signup);
				// sending mail to user about his dummy password creation
				EmailRequest emailRequest = EmailRequest.builder().subject("Verification Done Successfully.")
						.message("Your Verification has been done successfully. \n Your Dummy password for now is "
								+ dummyPassword)
						.to(email).build();
				emailService.sendEmail(emailRequest);
				res = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;

	}

	private String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj); // Use ObjectMapper to serialize to JSON
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{}"; // Return empty JSON in case of error
		}
	}

	public ResponseObject changePassword(String emailId, String oldPassword, String newPassword) throws Exception {

		ResponseObject responseObject = new ResponseObject();
		User user = userrepository.getByEmailId(emailId);

//			user = userDao.find(emailId);

		oldPassword = (PasswordHasher.hashPassword(oldPassword, user.getAppId()));

		newPassword = (PasswordHasher.hashPassword(newPassword, user.getAppId()));
		if (!oldPassword.equals(user.getPassword())) {
			responseObject.setResponseCode(ErrorType.PASSWORD_MISMATCH.getResponseCode());
			return responseObject;
		} else if (newPassword.equals(oldPassword)) { // Match if new and old password is same and password is correct
			responseObject.setResponseCode(ErrorType.OLD_PASSWORD_MATCH.getResponseCode());
			return responseObject;
		}

		if (isUsedPassword(newPassword, user.getEmailId())) {
			responseObject.setResponseCode(ErrorType.OLD_PASSWORD_MATCH.getResponseCode());
			return responseObject;
		}

//			userRecordsDao.createDetails(emailId, oldPassword, user.getAppId());
		UserRecords userRecords = UserRecords.builder().appId(user.getAppId()).emailId(emailId).password(oldPassword)
				.createDate(new Date()).build();
		userRecordsRepository.save(userRecords);

		user.setPassword(newPassword);
		// update the merchant /user to the db with new password
		userrepository.save(user);

		responseObject.setResponseCode(ErrorType.PASSWORD_CHANGED.getResponseCode());

		// Sending Email for CRM password change notification
//			EmailBuilder emailBuilder = new EmailBuilder();
//			emailBuilder.emailPasswordChange(responseObject,emailId);

		// sending mail to merchant for its password updation
		EmailRequest emailRequest = EmailRequest.builder().subject("Account Password Changed").message(
				"Your Account Password has been Changed Successfully. \n NOTE: Please contact if you have not did this.")
				.to(emailId).build();
		emailService.sendEmail(emailRequest);

		// Maintain Audit Trail for Password Changed

		AuditTrail auditTrail = AuditTrail.builder().action("Password Changed")
				.comments("Merchant Account Password has been changed").entityName("User Entity").ipAddress("127.0.0.1")
				.oldValue(oldPassword).newValue(newPassword).performedByUser("MERCHANT").updatedat(LocalDateTime.now()).performedAt(LocalDateTime.now())
				.build();

		// saving to db

		auditTrailService.saveAuditTrail(auditTrail);

		logger.info("Password changed successfully...");

		return responseObject;
	}

	public boolean isUsedPassword(String newPassword, String emailId) {
		List<String> oldPasswords = new ArrayList<String>();
		List<String> getAllPasswords = userRecordsRepository.findAll().stream()
				.filter(item -> item.getEmailId().equals(emailId)) // Filter the records where the email matches
				.map(item -> item.getPassword()) // Map to the password field
				.collect(Collectors.toList());
		oldPasswords = getAllPasswords;
		for (String password : oldPasswords) {
			if (null == password) {
				continue;
			}
			if (password.equals(newPassword)) {
				return true;
			}
		}
		return false;
	}

	public ChangePasswordResponse executeChangePassword(ChangePasswordRequest changePasswordRequest) {
		ResponseObject responseObject = new ResponseObject();
		try {
			responseObject = changePassword(changePasswordRequest.getUsername(), changePasswordRequest.getOldPassword(),
					changePasswordRequest.getNewPassword());

			if (responseObject.getResponseCode().equals(ErrorType.PASSWORD_MISMATCH.getResponseCode())) {
				return ChangePasswordResponse.builder().message(ErrorType.PASSWORD_MISMATCH.getResponseMessage()).status(HttpStatus.BAD_REQUEST).build();

			} else if (responseObject.getResponseCode().equals(ErrorType.OLD_PASSWORD_MATCH.getResponseCode())) {
				return ChangePasswordResponse.builder().message(ErrorType.OLD_PASSWORD_MATCH.getResponseMessage()).status(HttpStatus.BAD_REQUEST).build();
		
			}
			return ChangePasswordResponse.builder().message(ErrorType.PASSWORD_CHANGED.getResponseMessage()).status(HttpStatus.OK).build();

		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
		}
		return ChangePasswordResponse.builder().message(ErrorType.PASSWORD_CHANGED.getResponseMessage()).status(HttpStatus.OK).build();
	}




	 public List<LoginHistory> getLoginHistoryByUserId(String userId) {
		 
	        return loginhistoryrepository.findByEmailId(userId);
		// return findLoginHistoryByUser(emailId,usertype,strartfrom , length)
	    }

	 
	 // use latter
//	 Pageable pageable = PageRequest.of(startFrom / length, length, Sort.by("id").descending());
//	 Page<LoginHistory> loginHistories = loginHistoryRepository.findLoginHistoryByUser(emailId, userType, pageable);
//	 List<LoginHistory> historyList = loginHistories.getContent();

}


