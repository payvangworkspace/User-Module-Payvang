package com.Payvang.Login.DataAccess.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WhiteLableBranding {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long  id;
	private String appId;
	private String brandName;
	private String brandURL;
	private String pgURL;
	private String logoutURL;
	private String smsSenderId;
	private String emailSenderId;
	private boolean poweredByBrandingFlag;
	private boolean emailHeaderLogoFlag;
	private boolean paymentPageHeaderLogoFlag;
	private boolean brandImagesFlag;
	private boolean smsSenderIdFlag;
	private boolean senderEmailFlag;
	// reseller
	private String  senderEmail;
	private String supportEmail;
	private String supportContactNumber;
	
	private byte []   logo;
	private byte [] loginImage;
	private byte [] errorImage;
	private byte [] paymentPageImage;
	

}
