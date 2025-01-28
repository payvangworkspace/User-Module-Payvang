package com.Payvang.Login.DataAccess.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "notificationemailer")
public class NotificationEmailer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "appId")
	private String appId;

	@Column(name = "expressPayFlag", nullable = false)
	private Boolean expressPayFlag;

	@Column(name = "iframePaymentFlag", nullable = false)
	private Boolean iframePaymentFlag;

	@Column(name = "merchantHostedFlag", nullable = false)
	private Boolean merchantHostedFlag;

	@Column(name = "refundTransactionCustomerEmailFlag", nullable = false)
	private Boolean refundTransactionCustomerEmailFlag;

	@Column(name = "refundTransactionMerchantEmailFlag", nullable = false)
	private Boolean refundTransactionMerchantEmailFlag;

	@Column(name = "retryTransactionCustomeFlag", nullable = false)
	private Boolean retryTransactionCustomeFlag;

	@Column(name = "sendMultipleEmailer")
	private String sendMultipleEmailer;

	@Column(name = "surchargeFlag", nullable = false)
	private Boolean surchargeFlag;

	@Column(name = "transactionAuthenticationEmailFlag", nullable = false)
	private Boolean transactionAuthenticationEmailFlag;

	@Column(name = "transactionCustomerEmailFlag", nullable = false)
	private Boolean transactionCustomerEmailFlag;

	@Column(name = "transactionEmailerFlag", nullable = false)
	private Boolean transactionEmailerFlag;

	@Column(name = "transactionSmsFlag", nullable = false)
	private Boolean transactionSmsFlag;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Boolean getExpressPayFlag() {
		return expressPayFlag;
	}

	public void setExpressPayFlag(Boolean expressPayFlag) {
		this.expressPayFlag = expressPayFlag;
	}

	public Boolean getIframePaymentFlag() {
		return iframePaymentFlag;
	}

	public void setIframePaymentFlag(Boolean iframePaymentFlag) {
		this.iframePaymentFlag = iframePaymentFlag;
	}

	public Boolean getMerchantHostedFlag() {
		return merchantHostedFlag;
	}

	public void setMerchantHostedFlag(Boolean merchantHostedFlag) {
		this.merchantHostedFlag = merchantHostedFlag;
	}

	public Boolean getRefundTransactionCustomerEmailFlag() {
		return refundTransactionCustomerEmailFlag;
	}

	public void setRefundTransactionCustomerEmailFlag(Boolean refundTransactionCustomerEmailFlag) {
		this.refundTransactionCustomerEmailFlag = refundTransactionCustomerEmailFlag;
	}

	public Boolean getRefundTransactionMerchantEmailFlag() {
		return refundTransactionMerchantEmailFlag;
	}

	public void setRefundTransactionMerchantEmailFlag(Boolean refundTransactionMerchantEmailFlag) {
		this.refundTransactionMerchantEmailFlag = refundTransactionMerchantEmailFlag;
	}

	public Boolean getRetryTransactionCustomeFlag() {
		return retryTransactionCustomeFlag;
	}

	public void setRetryTransactionCustomeFlag(Boolean retryTransactionCustomeFlag) {
		this.retryTransactionCustomeFlag = retryTransactionCustomeFlag;
	}

	public String getSendMultipleEmailer() {
		return sendMultipleEmailer;
	}

	public void setSendMultipleEmailer(String sendMultipleEmailer) {
		this.sendMultipleEmailer = sendMultipleEmailer;
	}

	public Boolean getSurchargeFlag() {
		return surchargeFlag;
	}

	public void setSurchargeFlag(Boolean surchargeFlag) {
		this.surchargeFlag = surchargeFlag;
	}

	public Boolean getTransactionAuthenticationEmailFlag() {
		return transactionAuthenticationEmailFlag;
	}

	public void setTransactionAuthenticationEmailFlag(Boolean transactionAuthenticationEmailFlag) {
		this.transactionAuthenticationEmailFlag = transactionAuthenticationEmailFlag;
	}

	public Boolean getTransactionCustomerEmailFlag() {
		return transactionCustomerEmailFlag;
	}

	public void setTransactionCustomerEmailFlag(Boolean transactionCustomerEmailFlag) {
		this.transactionCustomerEmailFlag = transactionCustomerEmailFlag;
	}

	public Boolean getTransactionEmailerFlag() {
		return transactionEmailerFlag;
	}

	public void setTransactionEmailerFlag(Boolean transactionEmailerFlag) {
		this.transactionEmailerFlag = transactionEmailerFlag;
	}

	public Boolean getTransactionSmsFlag() {
		return transactionSmsFlag;
	}

	public void setTransactionSmsFlag(Boolean transactionSmsFlag) {
		this.transactionSmsFlag = transactionSmsFlag;
	}
}
