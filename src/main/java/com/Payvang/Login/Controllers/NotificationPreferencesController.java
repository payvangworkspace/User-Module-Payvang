package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Payvang.Login.CustomExceptions.CustomException;
import com.Payvang.Login.DataAccess.Models.MerchantNotificationPreferences;
import com.Payvang.Login.Models.NotificationPreferenceRequest;
import com.Payvang.Login.Services.NotificationPreferenceService;

@RestController
@RequestMapping("/api/notification/preferences/")
public class NotificationPreferencesController {

    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @PostMapping("/save")
    public ResponseEntity<?> savePreferences(@RequestBody NotificationPreferenceRequest request) {
        try {
            MerchantNotificationPreferences savedPreferences = notificationPreferenceService.savePreference(request);
            return new ResponseEntity<>(savedPreferences, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving preferences: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMerchantPreference(@PathVariable int id) {
        try {
            MerchantNotificationPreferences preferences = notificationPreferenceService.getMerchantNotificationPreference(id);
            return new ResponseEntity<>(preferences, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving preferences: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMerchantPreference(
            @PathVariable int id,
            @RequestBody NotificationPreferenceRequest request) {
        try {
            MerchantNotificationPreferences updatedPreferences = notificationPreferenceService.updateMerchantPreference(request, id);
            return new ResponseEntity<>(updatedPreferences, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating preferences: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchMerchantPreference(
            @PathVariable int id,
            @RequestBody NotificationPreferenceRequest request) {
        try {
            MerchantNotificationPreferences patchedPreferences = notificationPreferenceService.patchMerchantPreference(request, id);
            return new ResponseEntity<>(patchedPreferences, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error patching preferences: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
