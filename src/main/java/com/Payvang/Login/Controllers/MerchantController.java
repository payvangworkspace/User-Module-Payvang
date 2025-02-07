package com.Payvang.Login.Controllers;



import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.Payvang.Login.CustomExceptions.MerchantNotFoundException;
import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.DataAccess.Models.MerchantBank;
import com.Payvang.Login.Models.MerchantBankRequest;
import com.Payvang.Login.Models.MerchantDTO;
import com.Payvang.Login.Models.MerchantRequest;
import com.Payvang.Login.Services.MerchantBankService;
import com.Payvang.Login.Services.MerchantService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

	@Autowired
	private MerchantBankService merchantBankService;

	@Autowired
	private MerchantService merchantservice;

	@PostMapping("/addBank")
	public ResponseEntity<String> addMerchantBankDetails(@RequestBody MerchantBankRequest request) {
		try {
			merchantBankService.saveMerchantBankDetails(request);
			return new ResponseEntity<>("Merchant bank details saved successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error while saving merchant bank details", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<String> createMerchant(@RequestBody MerchantRequest request) {
		try {
			merchantservice.createMerchant(request);
			return new ResponseEntity<>("Merchant created successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error while creating merchant", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 @GetMapping("/{merchantId}")
	    public ResponseEntity<Merchant> getMerchant(@PathVariable ("merchantId")Long merchantId) {
	        try {
	            Merchant merchant = merchantservice.getMerchantById(merchantId);
	            return new ResponseEntity<>(merchant, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	 
	 @PutMapping("/{merchantId}")
	    public ResponseEntity<String> updateMerchant(@PathVariable ("merchantId") Long merchantId, @RequestBody MerchantRequest request) {
	        try {
	            merchantservice.updateMerchant(merchantId, request);
	            return new ResponseEntity<>("Merchant updated successfully", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error while updating merchant", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	 
	 @GetMapping("bank/{accountId}")
	    public ResponseEntity<MerchantBank> getMerchantBank(@PathVariable ("accountId")Long accountId) {
	        try {
	            MerchantBank merchantBank = merchantBankService.getMerchantBankById(accountId);
	            return new ResponseEntity<>(merchantBank, HttpStatus.OK);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	   
	    @PutMapping("updatebank/{accountId}")
	    public ResponseEntity<String> updateMerchantBank(@PathVariable ("accountId")Long accountId, @RequestBody MerchantBankRequest request) {
	        try {
	            merchantBankService.updateMerchantBank(accountId, request);
	            return new ResponseEntity<>("Merchant Bank account updated successfully", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error while updating merchant bank account", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    @PatchMapping("/partially/{id}")
	    public ResponseEntity<?> updateMerchantFields(@PathVariable("id") Long id,@RequestBody MerchantDTO updates) {
	        try {
	            Merchant updatedMerchant =  merchantservice.updateMerchant(id, updates);
	            return ResponseEntity.ok(updatedMerchant);
	        } catch (MerchantNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating merchant details");
	        }
	    }
	    
	    @GetMapping
	    public List<MerchantDTO> getAllMerchants() {
	        return merchantservice.getAllMerchants();
	    
	    }
	    
	    @GetMapping("/details/{appId}")
	    public ResponseEntity<?> getMerchantById(@PathVariable ("appId")String merchantId) {
	        try {
	            MerchantDTO merchantDTO = merchantservice.getMerchantDetailById(merchantId);
	            return ResponseEntity.ok(merchantDTO);
	        } catch (EntityNotFoundException ex) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(Collections.singletonMap("error", ex.getMessage()));
	        } catch (Exception ex) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Collections.singletonMap("error", "An unexpected error occurred."));
	        }
	    }

	
}
