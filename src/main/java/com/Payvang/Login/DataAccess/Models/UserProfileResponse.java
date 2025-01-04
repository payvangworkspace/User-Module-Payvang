package com.Payvang.Login.DataAccess.Models;

public class UserProfileResponse {

	private String firstName;
	private String lastName;
	private String businessName;

	public UserProfileResponse(String firstName, String lastName,String businessName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.businessName=businessName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	

}
