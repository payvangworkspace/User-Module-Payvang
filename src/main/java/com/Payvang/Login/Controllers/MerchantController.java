package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.Models.MerchantBankRequest;
import com.Payvang.Login.Models.MerchantRequest;
import com.Payvang.Login.Services.MerchantBankService;
import com.Payvang.Login.Services.MerchantService;

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

}
