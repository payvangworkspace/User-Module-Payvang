package com.Payvang.Login.Util;

import com.Payvang.Login.CustomExceptions.UnauthorizedException;
import com.Payvang.Login.Properties.PropertiesManager;

import jakarta.transaction.SystemException;

public class PasswordHasher {

	public static String hashPassword(String password,String appId) throws SystemException, com.Payvang.Login.CustomExceptions.SystemException
	{
	
		String salt = (new PropertiesManager()).getSalt(appId);	
		if(null==salt){
			throw new UnauthorizedException("Invalid app id");
		}
		
		String hashedPassword = Hasher.getHash(password.concat(salt));		
		return hashedPassword;		
	}		
}
