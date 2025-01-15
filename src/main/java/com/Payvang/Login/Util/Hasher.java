package com.Payvang.Login.Util;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.Payvang.Login.Properties.PropertiesManager;

import jakarta.transaction.SystemException;

public class Hasher {

	private static Logger logger = LoggerFactory.getLogger(Hasher.class.getName());

	public Hasher() {
	}

	public static String getHash(String input) throws SystemException, com.Payvang.Login.CustomExceptions.SystemException {
		String response = null;
		logger.info("Calculated Hash Param:" + input);
		MessageDigest messageDigest = MessageDigestProvider.provide();
		messageDigest.update(input.getBytes());
		MessageDigestProvider.consume(messageDigest);

		response = new String(Hex.encodeHex(messageDigest.digest()));
		logger.info("Calculated Hash :" + response.toUpperCase());
		return response.toUpperCase();
	}// getSHA256Hex()

	
}
