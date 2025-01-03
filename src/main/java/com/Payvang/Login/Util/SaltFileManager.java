package com.Payvang.Login.Util;

import com.Payvang.Login.Constants.Constants;
import com.Payvang.Login.Properties.PropertiesManager;

public class SaltFileManager extends PropertiesManager{

	public boolean insertSalt(String appId,String salt){
		PropertiesManager propertiesManager = new PropertiesManager();
		
		String saltPropertiesFile = propertiesManager.getSystemProperty(Constants.SALT_FILE_PATH_NAME.getValue()); 
		
		return setProperty(appId,salt,saltPropertiesFile);		
	}	
}
