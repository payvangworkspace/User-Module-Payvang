package com.Payvang.Login.DataAccess.Models;

public class UserProfileResponse {

	private String businessName;
	private String emailId;
	private String industryCategory;
	private String industrySubCategory;

	public UserProfileResponse(String businessName, String emailId, String industryCategory,
			String industrySubcategory) {

		this.businessName = businessName;
		this.emailId = emailId;
		this.industryCategory = industryCategory;
		this.industrySubCategory = industrySubcategory;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getIndustrySubCategory() {
		return industrySubCategory;
	}

	public void setIndustrySubCategory(String industrySubCategory) {
		this.industrySubCategory = industrySubCategory;
	}

}
