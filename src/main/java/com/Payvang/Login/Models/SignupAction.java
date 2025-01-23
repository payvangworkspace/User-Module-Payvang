package com.Payvang.Login.Models;

import lombok.Builder;

@Builder
public class SignupAction {

	private String emailId;
	private String password;
//	private String businessName;
//	private String mobile;
//	private String confirmPassword;
//	private String captcha;
//	private String userRoleType;
//	private String industryCategory;
//	private String industrySubCategory;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public String getBusinessName() {
//		return businessName;
//	}
//	public void setBusinessName(String businessName) {
//		this.businessName = businessName;
//	}
//	public String getMobile() {
//		return mobile;
//	}
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//	public String getConfirmPassword() {
//		return confirmPassword;
//	}
//	public void setConfirmPassword(String confirmPassword) {
//		this.confirmPassword = confirmPassword;
//	}
//	public String getCaptcha() {
//		return captcha;
//	}
//	public void setCaptcha(String captcha) {
//		this.captcha = captcha;
//	}
//	public String getUserRoleType() {
//		return userRoleType;
//	}
//	public void setUserRoleType(String userRoleType) {
//		this.userRoleType = userRoleType;
//	}
//	public String getIndustryCategory() {
//		return industryCategory;
//	}
//	public void setIndustryCategory(String industryCategory) {
//		this.industryCategory = industryCategory;
//	}
//	public String getIndustrySubCategory() {
//		return industrySubCategory;
//	}
//	public void setIndustrySubCategory(String industrySubCategory) {
//		this.industrySubCategory = industrySubCategory;
//	}
	
	
}
