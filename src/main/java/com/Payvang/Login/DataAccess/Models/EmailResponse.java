package com.Payvang.Login.DataAccess.Models;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailResponse {
	private String message;
	private HttpStatus status;
	

}
