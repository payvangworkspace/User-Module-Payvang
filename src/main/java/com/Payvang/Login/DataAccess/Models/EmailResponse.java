package com.Payvang.Login.DataAccess.Models;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class EmailResponse {
	private String message;
	private HttpStatus status;
	
	 public EmailResponse(String message, HttpStatus status) {
	        this.message = message;
	        this.status = status;
	    }
}
