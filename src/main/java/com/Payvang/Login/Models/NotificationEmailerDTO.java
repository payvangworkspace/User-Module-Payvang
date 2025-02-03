package com.Payvang.Login.Models;

public class NotificationEmailerDTO {

	private String appId;
	private Long merchantId;
	private Boolean expressPayFlag;
	private Boolean iframePaymentFlag;
	private Boolean merchantHostedFlag;
	private Boolean refundTransactionCustomerEmailFlag;
	private Boolean refundTransactionMerchantEmailFlag;
	private Boolean retryTransactionCustomeFlag;
	private String sendMultipleEmailer;
	private Boolean surchargeFlag;
	private Boolean transactionAuthenticationEmailFlag;
	private Boolean transactionCustomerEmailFlag;
	private Boolean transactionEmailerFlag;
	private Boolean transactionSmsFlag;

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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

}
