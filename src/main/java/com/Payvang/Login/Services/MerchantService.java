package com.Payvang.Login.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.CustomExceptions.MerchantNotFoundException;
import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.Models.MerchantDTO;
import com.Payvang.Login.Models.MerchantRequest;
import com.Payvang.Login.Repositories.MerchantRepository;

import jakarta.transaction.Transactional;


@Service
public class MerchantService {

	
	    @Autowired
	    private MerchantRepository merchantRepository;

	    // Method to create a new merchant
	    public void createMerchant(MerchantRequest request) {
	        Merchant merchant = new Merchant();
	        merchant.setBusinessName(request.getBusinessName());
	        merchant.setContactName(request.getContactName());
	        merchant.setContactEmail(request.getContactEmail());
	        merchant.setContactPhone(request.getContactPhone());
	        merchant.setAddress(request.getAddress());
	        merchant.setWebsite(request.getWebsite());
	        merchant.setCreatedAt(LocalDateTime.now());
	        merchant.setUpdatedAt(LocalDateTime.now());

	        merchantRepository.save(merchant);
	    }
	    
	    
	    // Get Merchant
	    public Merchant getMerchantById(Long merchantId) {
	        return merchantRepository.findById(merchantId)
	                .orElseThrow(() -> new RuntimeException("Merchant not found"));
	    }

	    // Method to update existing merchant
	    public void updateMerchant(Long merchantId, MerchantRequest request) {
	        Merchant merchant = merchantRepository.findById(merchantId)
	                .orElseThrow(() -> new RuntimeException("Merchant not found"));

	        merchant.setBusinessName(request.getBusinessName());
	        merchant.setContactName(request.getContactName());
	        merchant.setContactEmail(request.getContactEmail());
	        merchant.setContactPhone(request.getContactPhone());
	        merchant.setAddress(request.getAddress());
	        merchant.setWebsite(request.getWebsite());
	        merchant.setUpdatedAt(LocalDateTime.now());

	        merchantRepository.save(merchant);
	    }
	    
	    @Transactional
	    public Merchant updateMerchant(Long id, MerchantDTO updates) throws MerchantNotFoundException {
	        // Fetch the existing Merchant from the database
	        Merchant existingMerchant = merchantRepository.findById(id)
	                .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id " + id));

	        // Apply updates to mutable fields
	        if (updates.getBusinessName() != null) {
	            existingMerchant.setBusinessName(updates.getBusinessName());
	        }
	        if (updates.getContactName() != null) {
	            existingMerchant.setContactName(updates.getContactName());
	        }
	        if (updates.getContactPhone() != null) {
	            existingMerchant.setContactPhone(updates.getContactPhone());
	        }
	        if (updates.getAddress() != null) {
	            existingMerchant.setAddress(updates.getAddress());
	        }
	        if (updates.getCity() != null) {
	            existingMerchant.setCity(updates.getCity());
	        }
	        if (updates.getState() != null) {
	            existingMerchant.setState(updates.getState());
	        }
	        if (updates.getCountry() != null) {
	            existingMerchant.setCountry(updates.getCountry());
	        }
	        if (updates.getPostalCode() != null) {
	            existingMerchant.setPostalCode(updates.getPostalCode());
	        }
	        if (updates.getTelephoneNo() != null) {
	            existingMerchant.setTelephoneNo(updates.getTelephoneNo());
	        }
	        if (updates.getWebsite() != null) {
	            existingMerchant.setWebsite(updates.getWebsite());
	        }
	        if (updates.getOrganisationType() != null) {
	            existingMerchant.setOrganisationType(updates.getOrganisationType());
	        }
	        if (updates.getMultiCurrency() != null) {
	            existingMerchant.setMultiCurrency(updates.getMultiCurrency());
	        }
	        if (updates.getOperationAddress() != null) {
	            existingMerchant.setOperationAddress(updates.getOperationAddress());
	        }
	        if (updates.getOperationCity() != null) {
	            existingMerchant.setOperationCity(updates.getOperationCity());
	        }
	        if (updates.getDateOfEstablishment() != null) {
	            existingMerchant.setDateOfEstablishment(updates.getDateOfEstablishment());
	        }
	        if (updates.getPan() != null) {
	            existingMerchant.setPan(updates.getPan());
	        }
	        if (updates.getPanName() != null) {
	            existingMerchant.setPanName(updates.getPanName());
	        }
	        if (updates.getBusinessModel() != null) {
	            existingMerchant.setBusinessModel(updates.getBusinessModel());
	        }
	        if (updates.getOperationState() != null) {
	            existingMerchant.setOperationState(updates.getOperationState());
	        }
	        if (updates.getOperationPostalCode() != null) {
	            existingMerchant.setOperationPostalCode(updates.getOperationPostalCode());
	        }
	        if (updates.getCin() != null) {
	            existingMerchant.setCin(updates.getCin());
	        }

	        // Save and return the updated Merchant
	        return merchantRepository.save(existingMerchant);
	    }
	
}
