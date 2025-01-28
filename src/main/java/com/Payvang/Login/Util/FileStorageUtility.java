package com.Payvang.Login.Util;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.Payvang.Login.Properties.PropertiesManager;

import java.io.File;
import java.io.IOException;

@Component
public class FileStorageUtility {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileStorageUtility.class);

	

	public String saveFile(String saveName, String filename, MultipartFile controlFile, String appId) {

		
		PropertiesManager propertiesManager = new PropertiesManager();
		String destPath = propertiesManager.getSystemProperty("DocumentPath") + appId;

		String saveFilename = appId + "_" + saveName + getFileExtension(filename);

		File destFile = new File(destPath, saveFilename);

		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}

		try {

			controlFile.transferTo(destFile);
			logger.info("File saved successfully to: " + destFile.getAbsolutePath());
		} catch (IOException exception) {
			logger.error("Exception occurred while saving the file", exception);
			return "ERROR";
		}

		return "SUCCESS";
	}

	private String getFileExtension(String filename) {
		if (filename == null || !filename.contains(".")) {
			return "";
		}
		return filename.substring(filename.lastIndexOf("."));
	}
}