package com.Payvang.Login.Services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.CustomExceptions.SystemException;
import com.Payvang.Login.DataAccess.Models.LoginHistory;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Models.ValidateFields;
import com.Payvang.Login.Repositories.LoginHistoryRepository;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Util.CrmFieldType;
import com.Payvang.Login.Util.CrmValidator;
import com.Payvang.Login.Util.ErrorType;
import com.Payvang.Login.Util.JwtUtil;
import com.Payvang.Login.Util.PasswordHasher;
import com.Payvang.Login.Util.UserStatusType;

import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import net.sf.uadetector.ReadableUserAgent;


@Service
public class LoginAuthenticator {

	@Autowired
	UserRepository userrepository;

	@Autowired
	LoginHistoryRepository loginhistoryrepository;
	
	@Autowired
	JwtUtil jwtutil;

	private User user = null;

	LoginHistory loginhistory = new LoginHistory();
	private Date date = new Date();
	private CrmValidator validator = new CrmValidator();
	private ValidateFields validatevalue = new ValidateFields();

	public ResponseObject authenticate(String emailId, String password, String agent, String ip)
			throws SystemException, jakarta.transaction.SystemException {

		ResponseObject responseObject = new ResponseObject();

		boolean status;
		String failureReason = null;

		user = userrepository.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("user not found"));

		if (null == user) {
			responseObject.setResponseMessage(ErrorType.USER_NOT_FOUND.getResponseMessage());
			responseObject.setResponseCode(ErrorType.USER_NOT_FOUND.getResponseCode());
			return responseObject;
		}

		if (!(user.getUserStatus().equals(UserStatusType.ACTIVE)
				|| user.getUserStatus().equals(UserStatusType.TRANSACTION_BLOCKED)
				|| user.getUserStatus().equals(UserStatusType.SUSPENDED))) {
			responseObject.setResponseMessage(ErrorType.USER_INACTIVE.getResponseMessage());
			responseObject.setResponseCode(ErrorType.USER_INACTIVE.getResponseCode());

			status = false;
			failureReason = ErrorType.USER_INACTIVE.getInternalMessage();
			loginhistory.setEmailId(emailId);
			loginhistory.setBusinessName(agent);

			saveLoginDetails(agent, status, user, ip, failureReason);
			return responseObject;
		}

		password = PasswordHasher.hashPassword(password, user.getAppId());
		String userDBPassword = user.getPassword();
		if (StringUtils.isEmpty(userDBPassword)) {
			status = false;
			failureReason = ErrorType.USER_PASSWORD_NOT_SET.getInternalMessage();
			responseObject.setResponseMessage(ErrorType.USER_PASSWORD_NOT_SET.getResponseMessage());
			responseObject.setResponseCode(ErrorType.USER_PASSWORD_NOT_SET.getResponseCode());
		} else if (userDBPassword.equals(password)) {
			status = true;
			responseObject.setResponseCode(ErrorType.SUCCESS.getResponseCode());
			    String accessToken = jwtutil.generateToken(user.getEmailId());
				responseObject.setResponseMessage(accessToken);
		} else {
			status = false;
			failureReason = ErrorType.USER_PASSWORD_INCORRECT.getInternalMessage();
			responseObject.setResponseMessage(ErrorType.USER_PASSWORD_INCORRECT.getResponseMessage());
			responseObject.setResponseCode(ErrorType.USER_PASSWORD_INCORRECT.getResponseCode());
		}
		saveLoginDetails(agent, status, user, ip, failureReason);
		return responseObject;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void saveLoginDetails(String request, Boolean status, User user, String ip, String message) {

		UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
		ReadableUserAgent agent = parser.parse(request);

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String formattedDate = sdf.format(date);

		validateIp(ip);
		loginhistory.setIp(validatevalue.getValidIp());

		validateBrowser(agent.getName());
		loginhistory.setBrowser(validatevalue.getValidBrowser());

		validateOS(agent.getOperatingSystem().getName());
		loginhistory.setOs(validatevalue.getValidOperatingSystem());

		loginhistory.setBusinessName(user.getBusinessName());
		loginhistory.setEmailId(user.getEmailId());
		loginhistory.setTimeStamp(formattedDate);
		loginhistory.setStatus(status);
		loginhistory.setFailureReason(message);
		loginhistoryrepository.save(loginhistory);

	}

	// Validate Ip
	public void validateIp(String value) {

		if (validator.validateField(CrmFieldType.IP, value)) {
			validatevalue.setValidIp(value);
		} else {
			validatevalue.setValidIp("unknown");
		}
	}

	// Validate Browser
	public void validateBrowser(String value) {

		if (validator.validateField(CrmFieldType.BROWSER, value)) {
			validatevalue.setValidBrowser(value);
		} else {
			validatevalue.setValidBrowser("unknown");
		}

	}

	// Validate Operating System
	public void validateOS(String value) {

		if (validator.validateField(CrmFieldType.OPERATINGSYSTEM, value)) {
			validatevalue.setValidOperatingSystem(value);
		} else {
			validatevalue.setValidOperatingSystem("unknown");
		}

	}

}
