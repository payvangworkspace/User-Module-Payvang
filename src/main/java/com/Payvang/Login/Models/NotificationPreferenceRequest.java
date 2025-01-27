package com.Payvang.Login.Models;

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
public class NotificationPreferenceRequest {
	private int merchantId;
	private boolean channelSms;
	private boolean channelEmail;
	private boolean channelWhatsapp;
	private boolean channelInApp;
	
	
	
	}
