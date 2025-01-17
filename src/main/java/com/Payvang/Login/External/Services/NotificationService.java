package com.Payvang.Login.External.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient
public interface NotificationService {	
	
	@PostMapping("/sendEmail")
	public 

}
