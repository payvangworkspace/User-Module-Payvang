package com.Payvang.Login.Services;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        try {
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

            MerchantNotificationPreferences preferences = MerchantNotificationPreferences.builder()
                .merchantId(notificationPreferenceRequest.getMerchantId())
                .channelSms(notificationPreferenceRequest.isChannelSms())
                .channelEmail(notificationPreferenceRequest.isChannelEmail())
                .channelWhatsapp(notificationPreferenceRequest.isChannelWhatsapp())
                .channelInApp(notificationPreferenceRequest.isChannelInApp())
                .createdAt(timestamp)
                .build();

            return notificationPreferencesRepo.save(preferences);
        } catch (DataAccessException e) {
            throw new CustomException("Error saving preferences: " + e.getMessage());
        }
    }

    public MerchantNotificationPreferences getMerchantNotificationPreference(int merchantId) {
        try {
            return notificationPreferencesRepo.findByMerchantId(merchantId)
                .orElseThrow(() -> new CustomException("Notification preference not found for merchant ID: " + merchantId));
        } catch (DataAccessException e) {
            throw new CustomException("Error retrieving preferences: " + e.getMessage());
        }
    }

    public MerchantNotificationPreferences updateMerchantPreference(NotificationPreferenceRequest notificationPreferenceRequest, int merchantId) {
        try {
            MerchantNotificationPreferences existingPreferences = notificationPreferencesRepo.findByMerchantId(merchantId)
                .orElseThrow(() -> new CustomException("Notification preference not found for merchant ID: " + merchantId));

            existingPreferences.setChannelSms(notificationPreferenceRequest.isChannelSms());
            existingPreferences.setChannelEmail(notificationPreferenceRequest.isChannelEmail());
            existingPreferences.setChannelWhatsapp(notificationPreferenceRequest.isChannelWhatsapp());
            existingPreferences.setChannelInApp(notificationPreferenceRequest.isChannelInApp());
            existingPreferences.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            return notificationPreferencesRepo.save(existingPreferences);
        } catch (DataAccessException e) {
            throw new CustomException("Error updating preferences: " + e.getMessage());
        }
    }

    public MerchantNotificationPreferences patchMerchantPreference(NotificationPreferenceRequest notificationPreferenceRequest, int merchantId) {
        try {
            MerchantNotificationPreferences existingPreferences = notificationPreferencesRepo.findByMerchantId(merchantId)
                .orElseThrow(() -> new CustomException("Notification preference not found for merchant ID: " + merchantId));

            existingPreferences.setChannelSms(notificationPreferenceRequest.isChannelSms());
            existingPreferences.setChannelEmail(notificationPreferenceRequest.isChannelEmail());
            existingPreferences.setChannelWhatsapp(notificationPreferenceRequest.isChannelWhatsapp());
            existingPreferences.setChannelInApp(notificationPreferenceRequest.isChannelInApp());
            existingPreferences.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            return notificationPreferencesRepo.save(existingPreferences);
        } catch (DataAccessException e) {
            throw new CustomException("Error patching preferences: " + e.getMessage());
        }
    }
}
