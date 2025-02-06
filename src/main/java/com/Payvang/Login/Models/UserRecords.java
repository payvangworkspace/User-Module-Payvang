package com.Payvang.Login.Models;

import java.util.Date;

import org.hibernate.annotations.Proxy;

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
@Proxy(lazy= false)
@Table(name="User_Records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRecords {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String emailId;
	private String password;
	private String appId; 
	private Date createDate;
	private Date updateDate;
}
