package com.Payvang.Login.DataAccess.Models;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "business_name", length = 255)
    private String businessName;

    @Column(name = "contact_name", length = 150)
    private String contactName;

    @Column(name = "contact_email", length = 150)
    private String contactEmail;

    @Column(name = "contact_phone", length = 15)
    private String contactPhone;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "telephone_no", length = 15)
    private String telephoneNo;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "organisation_type", length = 100)
    private String organisationType;

    @Column(name = "multi_currency")
    private Boolean multiCurrency;

    @Column(name = "operation_address", length = 255)
    private String operationAddress;

    @Column(name = "operation_city", length = 100)
    private String operationCity;

    @Column(name = "date_of_establishment")
    private LocalDateTime dateOfEstablishment;

    @Column(name = "pan", length = 10)
    private String pan;

    @Column(name = "pan_name", length = 100)
    private String panName;

    @Column(name = "business_model", length = 255)
    private String businessModel;

    @Column(name = "operation_state", length = 100)
    private String operationState;

    @Column(name = "operation_postal_code", length = 10)
    private String operationPostalCode;

    @Column(name = "cin", length = 21)
    private String cin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MerchantBank> bankAccounts;
    
    @OneToOne(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NotificationEmailer notificationEmailer; 
    
    @Column(name = "app_id", length = 255, nullable = true, insertable = false, updatable = false)
    private String appId;

    @OneToOne
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private User user;

    
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public List<MerchantBank> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<MerchantBank> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public NotificationEmailer getNotificationEmailer() {
		return notificationEmailer;
	}

	public void setNotificationEmailer(NotificationEmailer notificationEmailer) {
		this.notificationEmailer = notificationEmailer;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}
