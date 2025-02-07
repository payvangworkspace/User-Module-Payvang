package com.Payvang.Login.DataAccess.Models;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantNotificationPreferences {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int notificationId;
	private int merchantId;
	private boolean channelSms;
	private boolean channelEmail;
	private boolean channelInApp;
	private boolean channelWhatsapp;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	}