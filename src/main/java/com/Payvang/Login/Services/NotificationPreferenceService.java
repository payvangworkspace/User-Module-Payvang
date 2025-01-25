package com.Payvang.Login.Services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.CustomExceptions.CustomException;
import com.Payvang.Login.DataAccess.Models.MerchantNotificationPreferences;
import com.Payvang.Login.Models.NotificationPreferenceRequest;
import com.Payvang.Login.Repositories.NotificationPreferences;

@Service
public class NotificationPreferenceService {
	
	
	@Autowired
	private NotificationPreferences notificationPreferencesRepo;
	
	
	public MerchantNotificationPreferences savePreference(NotificationPreferenceRequest notificationPreferenceRequest) {

		  // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Convert to Timestamp
        Timestamp timestamp = Timestamp.valueOf(now);
        
        //getting merchant id from 
        	
		MerchantNotificationPreferences merchantNotificationPreferences=MerchantNotificationPreferences.builder().channelEmail(notificationPreferenceRequest.isChannelEmail()).channelInApp(notificationPreferenceRequest.isChannelInApp()).channelSms(notificationPreferenceRequest.isChannelSms())
				.createdAt(timestamp).merchantId(notificationPreferenceRequest.getMerchantId())
				.build();
		
		//saving into db
		MerchantNotificationPreferences merchantNotificationPreferences2=notificationPreferencesRepo.save(merchantNotificationPreferences);
		return merchantNotificationPreferences2;
		
		
	}
	
	public MerchantNotificationPreferences getMerchantNotificationPreference(int merchantId) {
		MerchantNotificationPreferences merchantNotificationPreferences=notificationPreferencesRepo.findByMerchantId(merchantId).orElseThrow(()->new CustomException("Notification Preference does not exist for this merchant"));
		return merchantNotificationPreferences;
	}
	
	
	
	

}
