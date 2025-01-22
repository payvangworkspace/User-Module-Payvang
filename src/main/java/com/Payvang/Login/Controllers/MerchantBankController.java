package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Payvang.Login.Models.MerchantBankRequest;
import com.Payvang.Login.Services.MerchantBankService;

@RestController
@RequestMapping("/api/merchantBank")
public class MerchantBankController {

	@Autowired
	private MerchantBankService merchantBankService;

	@PostMapping("/add")
	public ResponseEntity<String> addMerchantBankDetails(@RequestBody MerchantBankRequest request) {
		try {
			merchantBankService.saveMerchantBankDetails(request);
			return new ResponseEntity<>("Merchant bank details saved successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error while saving merchant bank details", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
