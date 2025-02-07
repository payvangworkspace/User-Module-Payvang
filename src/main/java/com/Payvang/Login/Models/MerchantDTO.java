package com.Payvang.Login.Models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class MerchantDTO {

	private String businessName;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String address;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String telephoneNo;
	private String website;
	private String organisationType;
	private Boolean multiCurrency;
	private String operationAddress;
	private String operationCity;
	private LocalDateTime dateOfEstablishment;
	private String pan;
	private String panName;
	private String businessModel;
	private String operationState;
	private String operationPostalCode;
	private String cin;
	private Long merchantId;
	private String appId;
	private List<MerchantBankRequest> bankAccounts;
	private NotificationEmailerDTO notificationEmailer;
	
	
	private String firstName;
	private String lastName;

	public List<MerchantBankRequest> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<MerchantBankRequest> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public MerchantDTO() {
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}

	public Boolean getMultiCurrency() {
		return multiCurrency;
	}

	public void setMultiCurrency(Boolean multiCurrency) {
		this.multiCurrency = multiCurrency;
	}

	public String getOperationAddress() {
		return operationAddress;
	}

	public void setOperationAddress(String operationAddress) {
		this.operationAddress = operationAddress;
	}

	public String getOperationCity() {
		return operationCity;
	}

	public void setOperationCity(String operationCity) {
		this.operationCity = operationCity;
	}

	public LocalDateTime getDateOfEstablishment() {
		return dateOfEstablishment;
	}

	public void setDateOfEstablishment(LocalDateTime dateOfEstablishment) {
		this.dateOfEstablishment = dateOfEstablishment;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPanName() {
		return panName;
	}

	public void setPanName(String panName) {
		this.panName = panName;
	}

	public String getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}

	public String getOperationState() {
		return operationState;
	}

	public void setOperationState(String operationState) {
		this.operationState = operationState;
	}

	public String getOperationPostalCode() {
		return operationPostalCode;
	}

	public void setOperationPostalCode(String operationPostalCode) {
		this.operationPostalCode = operationPostalCode;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public NotificationEmailerDTO getNotificationEmailer() {
		return notificationEmailer;
	}

	public void setNotificationEmailer(NotificationEmailerDTO notificationEmailer) {
		this.notificationEmailer = notificationEmailer;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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
	
	

}
