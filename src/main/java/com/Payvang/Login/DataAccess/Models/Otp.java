package com.Payvang.Login.DataAccess.Models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "otp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, length = 6)
	private String otp;

	@Column(nullable = false)
	private Date triggerTime;

	@Column(nullable = false)
	private Date expiryTime;
	
	@Column(nullable=false)
	private String recipient;
}