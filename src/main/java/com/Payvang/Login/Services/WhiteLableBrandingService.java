package com.Payvang.Login.Services;

import com.Payvang.Login.DataAccess.Models.WhiteLableBranding;
import com.Payvang.Login.DataAccess.Models.AuditTrail;
import com.Payvang.Login.Repositories.WhiteLableBrandingRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WhiteLableBrandingService {

    @Autowired
    private WhiteLableBrandingRepo whiteLableBrandingRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Save new branding details.
     */
    public WhiteLableBranding saveBranding(String appId, MultipartFile loginImage,
                                           MultipartFile logoImage, MultipartFile errorImage,
                                           MultipartFile paymentPageImage, String performedBy, String ipAddress) throws IOException {
        WhiteLableBranding branding = new WhiteLableBranding();
        branding.setAppId(appId);

        // Set only non-null images
        if (loginImage != null && !loginImage.isEmpty()) {
            branding.setLoginImage(loginImage.getBytes());
        }
        if (logoImage != null && !logoImage.isEmpty()) {
            branding.setLogo(logoImage.getBytes());
        }
        if (errorImage != null && !errorImage.isEmpty()) {
            branding.setErrorImage(errorImage.getBytes());
        }
        if (paymentPageImage != null && !paymentPageImage.isEmpty()) {
            branding.setPaymentPageImage(paymentPageImage.getBytes());
        }

        WhiteLableBranding savedBranding = whiteLableBrandingRepository.save(branding);
        return savedBranding;
    }

    /**
     * Update existing branding details.
     */
    public WhiteLableBranding updateBranding(String appId, MultipartFile loginImage,
                                             MultipartFile logoImage, MultipartFile errorImage,
                                             MultipartFile paymentPageImage, String performedBy, String ipAddress) throws IOException {
        WhiteLableBranding branding = whiteLableBrandingRepository.findByAppId(appId);

        if (branding == null) {
            throw new RuntimeException("Branding not found for appId: " + appId);
        }

        String oldValue = toJson(branding);  // Serialize the old branding object to JSON
        boolean isUpdated = false;

        // Set only non-null images and check if any update happened
        if (loginImage != null && !loginImage.isEmpty()) {
            if (branding.getLoginImage() == null || !branding.getLoginImage().equals(loginImage.getBytes())) {
                branding.setLoginImage(loginImage.getBytes());
                isUpdated = true;
            }
        }
        if (logoImage != null && !logoImage.isEmpty()) {
            if (branding.getLogo() == null || !branding.getLogo().equals(logoImage.getBytes())) {
                branding.setLogo(logoImage.getBytes());
                isUpdated = true;
            }
        }
        if (errorImage != null && !errorImage.isEmpty()) {
            if (branding.getErrorImage() == null || !branding.getErrorImage().equals(errorImage.getBytes())) {
                branding.setErrorImage(errorImage.getBytes());
                isUpdated = true;
            }
        }
        if (paymentPageImage != null && !paymentPageImage.isEmpty()) {
            if (branding.getPaymentPageImage() == null || !branding.getPaymentPageImage().equals(paymentPageImage.getBytes())) {
                branding.setPaymentPageImage(paymentPageImage.getBytes());
                isUpdated = true;
            }
        }

        WhiteLableBranding savedBranding = whiteLableBrandingRepository.save(branding);

        // Save audit trail only if there is an update
        if (isUpdated) {
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setEntityName("WhiteLableBranding");
            auditTrail.setAction("UPDATE");
            auditTrail.setPerformedByUser(performedBy);
            auditTrail.setPerformedAt(LocalDateTime.now());
            auditTrail.setUpdatedat(LocalDateTime.now());
            auditTrail.setOldValue(oldValue);  // Save old value as JSON
            auditTrail.setNewValue(toJson(savedBranding));  // Save new value as JSON
            auditTrail.setIpAddress(ipAddress);
            auditTrail.setComments("Branding details updated");

            auditTrailService.saveAuditTrail(auditTrail);
        }

        return savedBranding;
    }

    /**
     * Retrieve branding details by appId.
     */
    public Optional<WhiteLableBranding> getBrandingByAppId(String appId) {
        return Optional.ofNullable(whiteLableBrandingRepository.findByAppId(appId));
    }

    /**
     * Retrieve branding details by ID.
     */
    public WhiteLableBranding getBrandingById(Long id) {
        return whiteLableBrandingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branding not found with id: " + id));
    }

    /**
     * Delete branding details by ID.
     */
    public void deleteBranding(Long id, String performedBy, String ipAddress) {
        WhiteLableBranding branding = getBrandingById(id);
        whiteLableBrandingRepository.deleteById(id);

        // Save audit trail for delete
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntityName("WhiteLableBranding");
        auditTrail.setAction("DELETE");
        auditTrail.setPerformedByUser(performedBy);
        auditTrail.setPerformedAt(LocalDateTime.now());
        auditTrail.setUpdatedat(LocalDateTime.now());
        auditTrail.setOldValue(toJson(branding));  // Save old value as JSON
        auditTrail.setNewValue("Deleted");
        auditTrail.setIpAddress(ipAddress);
        auditTrail.setComments("Branding details deleted");

        auditTrailService.saveAuditTrail(auditTrail);
    }

    /**
     * Convert an object to its JSON representation.
     */
    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);  // Use ObjectMapper to serialize to JSON
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";  // Return empty JSON in case of error
        }
    }
}
