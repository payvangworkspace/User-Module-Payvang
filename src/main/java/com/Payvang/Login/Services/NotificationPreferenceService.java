package com.Payvang.Login.Services;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.Payvang.Login.CustomExceptions.CustomException;
import com.Payvang.Login.DataAccess.Models.AuditTrail;
import com.Payvang.Login.DataAccess.Models.MerchantNotificationPreferences;
import com.Payvang.Login.Models.NotificationPreferenceRequest;
import com.Payvang.Login.Repositories.NotificationPreferences;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationPreferenceService {

	@Autowired
	private NotificationPreferences notificationPreferencesRepo;

	@Autowired
	private AuditTrailService auditTrailService;

	@Autowired
	private ObjectMapper objectMapper; // For JSON conversion

	public MerchantNotificationPreferences savePreference(NotificationPreferenceRequest notificationPreferenceRequest) {
		try {
			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

			MerchantNotificationPreferences preferences = MerchantNotificationPreferences.builder()
					.merchantId(notificationPreferenceRequest.getMerchantId())
					.channelSms(notificationPreferenceRequest.isChannelSms())
					.channelEmail(notificationPreferenceRequest.isChannelEmail())
					.channelWhatsapp(notificationPreferenceRequest.isChannelWhatsapp())
					.channelInApp(notificationPreferenceRequest.isChannelInApp()).createdAt(timestamp).build();

			return notificationPreferencesRepo.save(preferences);
		} catch (DataAccessException e) {
			throw new CustomException("Error saving preferences: " + e.getMessage());
		}
	}

	public MerchantNotificationPreferences getMerchantNotificationPreference(int merchantId) {
		try {
			return notificationPreferencesRepo.findByMerchantId(merchantId).orElseThrow(
					() -> new CustomException("Notification preference not found for merchant ID: " + merchantId));
		} catch (DataAccessException e) {
			throw new CustomException("Error retrieving preferences: " + e.getMessage());
		}
	}

	public MerchantNotificationPreferences updateMerchantPreference(
			NotificationPreferenceRequest notificationPreferenceRequest, int merchantId) {
		try {
			MerchantNotificationPreferences existingPreferences = notificationPreferencesRepo
					.findByMerchantId(merchantId).orElseThrow(() -> new CustomException(
							"Notification preference not found for merchant ID: " + merchantId));

			// Capture old values
			String oldValues = convertToJson(existingPreferences);
			existingPreferences.setChannelSms(notificationPreferenceRequest.isChannelSms());
			existingPreferences.setChannelEmail(notificationPreferenceRequest.isChannelEmail());
			existingPreferences.setChannelWhatsapp(notificationPreferenceRequest.isChannelWhatsapp());
			existingPreferences.setChannelInApp(notificationPreferenceRequest.isChannelInApp());
			existingPreferences.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

			// saving the audit trails

			// Capture new values
			String newValues = convertToJson(existingPreferences);

			MerchantNotificationPreferences updatedPreferences = notificationPreferencesRepo.save(existingPreferences);
			// Save audit trail
			// Save audit trail
			AuditTrail auditTrail = AuditTrail.builder().entityName("MerchantNotificationPreferences")
					.updatedat(LocalDateTime.now()).action("UPDATE").performedByUser("MERCHANT") // Replace with actual user
																							// details if available
					.performedAt(LocalDateTime.now()).oldValue(oldValues).newValue(newValues).ipAddress("127.0.0.1") // Fetch
																														// from
																														// request
																														// if
																														// available
					.comments("Updated notification preferences for merchant ID: " + merchantId).build();

			auditTrailService.saveAuditTrail(auditTrail);

			return updatedPreferences;
		} catch (DataAccessException e) {
			throw new CustomException("Error updating preferences: " + e.getMessage());
		}
	}

	public MerchantNotificationPreferences patchMerchantPreference(
			NotificationPreferenceRequest notificationPreferenceRequest, int merchantId) {
		try {
			MerchantNotificationPreferences existingPreferences = notificationPreferencesRepo
					.findByMerchantId(merchantId).orElseThrow(() -> new CustomException(
							"Notification preference not found for merchant ID: " + merchantId));

			// Capture old values
			String oldValues = convertToJson(existingPreferences);

			existingPreferences.setChannelSms(notificationPreferenceRequest.isChannelSms());
			existingPreferences.setChannelEmail(notificationPreferenceRequest.isChannelEmail());
			existingPreferences.setChannelWhatsapp(notificationPreferenceRequest.isChannelWhatsapp());
			existingPreferences.setChannelInApp(notificationPreferenceRequest.isChannelInApp());
			existingPreferences.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
			// Capture new values
			String newValues = convertToJson(existingPreferences);

			// Save the updated entity
			MerchantNotificationPreferences updatedPreferences = notificationPreferencesRepo.save(existingPreferences);
			// Save audit trail
			AuditTrail auditTrail = AuditTrail.builder().entityName("MerchantNotificationPreferences")
					.updatedat(LocalDateTime.now()).action("PATCH").performedByUser("System") // Replace with actual user
																							// details if available
					.performedAt(LocalDateTime.now()).oldValue(oldValues).newValue(newValues).ipAddress("127.0.0.1") // Fetch
																														// from
																														// request
																														// if
																														// available
					.comments("Patched notification preferences for merchant ID: " + merchantId).build();

			auditTrailService.saveAuditTrail(auditTrail);

			return updatedPreferences;
		} catch (DataAccessException e) {
			throw new CustomException("Error patching preferences: " + e.getMessage());
		}
	}

	private String convertToJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "Error converting to JSON: " + e.getMessage();
		}
	}

}
