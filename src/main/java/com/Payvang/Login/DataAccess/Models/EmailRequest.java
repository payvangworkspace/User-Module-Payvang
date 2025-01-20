package com.Payvang.Login.DataAccess.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {
	private String subject;
	private String to;
	private String message;
	

}
