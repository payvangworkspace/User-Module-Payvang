package com.Payvang.Login.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.Models.MerchantRequest;
import com.Payvang.Login.Repositories.MerchantRepository;


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
}
