package com.Payvang.Login.Services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.Constants.UserType;
import com.Payvang.Login.CustomExceptions.SystemException;
import com.Payvang.Login.DataAccess.Models.EmailRequest;
import com.Payvang.Login.DataAccess.Models.EmailResponse;
import com.Payvang.Login.DataAccess.Models.PermissionType;
import com.Payvang.Login.DataAccess.Models.Permissions;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.DataAccess.Models.Roles;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.External.Services.EmailService;
import com.Payvang.Login.Models.CreateSubAdminDTO;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Util.ErrorType;
import com.Payvang.Login.Util.Hasher;
import com.Payvang.Login.Util.SaltFactory;
import com.Payvang.Login.Util.SaltFileManager;
import com.Payvang.Login.Util.TransactionManager;
import com.Payvang.Login.Util.UserStatusType;

@Service
public class CreateSubAdmin {

	@Autowired
	UserRepository userrepository;
	
	@Autowired
	EmailService emailservice;

	public ResponseObject CreateSubAdminstration(CreateSubAdminDTO requestbody)
			throws SystemException, jakarta.transaction.SystemException {
		String name = requestbody.getFirstName();
		User user = new User();
		ResponseObject responseObject = new ResponseObject();
		requestbody.setListPermissionType(PermissionType.getPermissionType());
		List<String> lstPermissiontType = requestbody.getLstPermissionType();
		Set<Permissions> permissions = new HashSet<Permissions>();
		user.setFirstName(requestbody.getFirstName());
		user.setLastName(requestbody.getLastName());
		user.setMobile(requestbody.getMobile());
		user.setEmailId(requestbody.getEmailId());
		if (requestbody.getListPermissionType().isEmpty()) {

		} else {
			for (String permissionType : lstPermissiontType) {
				Permissions permission = new Permissions();
				permission.setPermissionType(PermissionType.getInstanceFromName(permissionType));
				permissions.add(permission);
			}
		}
		System.out.println(permissions+" permissions for subusers ");
		responseObject = createNewSubAdmin(user, UserType.SUBADMIN, " ", permissions);
		
		EmailRequest emailRequest = EmailRequest.builder().to(requestbody.getEmailId())
				.message("Dear " +name+ " ,Please click on below button to set your account password..  http://localhost:3000/verify?id=")
				.subject("Congratulations your account has been registered.Please Set Your password").build();
		EmailResponse emailResponse = emailservice.sendEmail(emailRequest);
		
		return responseObject;
	}

	public ResponseObject createNewSubAdmin(User user, UserType userType, String parentappId,
			Set<Permissions> permissions) throws SystemException, jakarta.transaction.SystemException {

		SaltFileManager saltFileManager = new SaltFileManager();

		ResponseObject responseObject = new ResponseObject();
		ResponseObject responseActionObject = new ResponseObject();

		if (user.getEmailId() != null) {
			User userdata = userrepository.getByEmailId(user.getEmailId());
			if (userdata != null) {
				responseObject.setResponseCode(ErrorType.USER_UNAVAILABLE.getResponseCode());
			}
		}

		Date date = new Date();
		String salt = SaltFactory.generateRandomSalt();

		if (ErrorType.USER_AVAILABLE.getResponseCode().equals(responseObject.getResponseCode())) {
			user.setUserType(userType);
			user.setUserStatus(UserStatusType.ACTIVE);
			user.setAppId(getappId());
			user.setAccountValidationKey(TransactionManager.getNewTransactionId());
			user.setEmailValidationFlag(false);

			user.setRegistrationDate(date);

			if (null != user.getPassword()) {
				user.setPassword(Hasher.getHash(user.getPassword().concat(salt)));
			} else {
				user.setPassword("");
			}
			user.setParentappId(parentappId);
			Set<Roles> roles = new HashSet<Roles>();
			Roles role = new Roles();
             System.out.println(permissions+ " here permissions ");
			role.setPermissions(permissions);
			role.setName(UserType.ADMIN.name());
			roles.add(role);

			user.setRoles(roles);

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
}
