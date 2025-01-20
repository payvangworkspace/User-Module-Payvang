package com.Payvang.Login.External.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Payvang.Login.DataAccess.Models.EmailRequest;
import com.Payvang.Login.DataAccess.Models.EmailResponse;

@FeignClient(name = "NOTIFICATIONSERVICE")
public interface EmailService {

	@PostMapping("/notify/sendEmail")
	public EmailResponse sendEmail(EmailRequest emailRequest);
	

}
 