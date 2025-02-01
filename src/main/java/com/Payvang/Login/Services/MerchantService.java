package com.Payvang.Login.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.CustomExceptions.MerchantNotFoundException;
import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.Models.MerchantBankRequest;
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
	    
	    public List<MerchantDTO> getAllMerchants() {
	        List<Merchant> merchants = merchantRepository.findAll();

	        return merchants.stream().map(merchant -> {
	            MerchantDTO dto = new MerchantDTO();
	            dto.setMerchantId(merchant.getMerchantId());
	            dto.setBusinessName(merchant.getBusinessName());
	            dto.setContactEmail(merchant.getContactEmail());
	            dto.setContactName(merchant.getContactName());
	            dto.setContactPhone(merchant.getContactPhone());
	            dto.setWebsite(merchant.getWebsite());
	            dto.setCity(merchant.getCity());
	            dto.setCountry(merchant.getCountry());
	            dto.setPostalCode(merchant.getPostalCode());
	            dto.setState(merchant.getState());
	            dto.setBusinessModel(merchant.getBusinessModel());
	            dto.setOrganisationType(merchant.getOrganisationType());
	            dto.setPan(merchant.getPan());
	            dto.setPanName(merchant.getPanName());

	            // Convert MerchantBank entities to DTOs
	            dto.setBankAccounts(merchant.getBankAccounts().stream().map(bank -> {
	                MerchantBankRequest bankDTO = new MerchantBankRequest();
	                bankDTO.setAccountHolderName(bank.getAccountHolderName());
	                bankDTO.setAccountNumber(bank.getAccountNumber());
	                bankDTO.setAccountType(bank.getAccountType().toString());
	                bankDTO.setBankName(bank.getBankName());
	                bankDTO.setBranchName(bank.getBranchName());
	                bankDTO.setIfscCode(bank.getIfscCode());
	                bankDTO.setStatus(bank.getStatus().toString());
	                return bankDTO;
	            }).collect(Collectors.toList()));

	            return dto;
	        }).collect(Collectors.toList());
	    }
	    
	    public MerchantDTO getMerchantDetailById(Long merchantId) {
	    	  Merchant merchant = merchantRepository.findById(merchantId)
		                .orElseThrow(() -> new RuntimeException("Merchant not found"));

	        MerchantDTO dto = new MerchantDTO();
	        dto.setMerchantId(merchant.getMerchantId());
	        dto.setBusinessName(merchant.getBusinessName());
	        dto.setContactEmail(merchant.getContactEmail());
	        dto.setContactName(merchant.getContactName());
	        dto.setContactPhone(merchant.getContactPhone());
	        dto.setWebsite(merchant.getWebsite());
	        dto.setCity(merchant.getCity());
	        dto.setCountry(merchant.getCountry());
	        dto.setPostalCode(merchant.getPostalCode());
	        dto.setState(merchant.getState());
	        dto.setBusinessModel(merchant.getBusinessModel());
	        dto.setOrganisationType(merchant.getOrganisationType());
	        dto.setPan(merchant.getPan());
	        dto.setPanName(merchant.getPanName());

	        // Convert MerchantBank entities to DTOs
	        dto.setBankAccounts(merchant.getBankAccounts().stream().map(bank -> {
	            MerchantBankRequest bankDTO = new MerchantBankRequest();
	            bankDTO.setAccountHolderName(bank.getAccountHolderName());
	            bankDTO.setAccountNumber(bank.getAccountNumber());
	            bankDTO.setAccountType(bank.getAccountType().toString());
	            bankDTO.setBankName(bank.getBankName());
	            bankDTO.setBranchName(bank.getBranchName());
	            bankDTO.setIfscCode(bank.getIfscCode());
	            bankDTO.setStatus(bank.getStatus().toString());
	            return bankDTO;
	        }).collect(Collectors.toList()));

	        return dto;
	    }

	
}
