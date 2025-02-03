package com.Payvang.Login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.DataAccess.Models.NotificationEmailer;
import com.Payvang.Login.Models.NotificationEmailerDTO;
import com.Payvang.Login.Repositories.MerchantRepository;
import com.Payvang.Login.Repositories.NotificationEmailerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationEmailerService {

	private final NotificationEmailerRepository repository;
	
	@Autowired
	MerchantRepository merchantrepository;

	@Autowired
	public NotificationEmailerService(NotificationEmailerRepository repository) {
		this.repository = repository;
	}

	public List<NotificationEmailer> getAllNotificationEmailers() {
		return repository.findAll();
	}

	public Optional<NotificationEmailer> getNotificationEmailerById(Long id) {
		return repository.findById(id);
	}

	public NotificationEmailer getNotificationEmailerByAppId(String appId) {
		return repository.findByAppId(appId);
	}

	public NotificationEmailer saveOrUpdateNotificationEmailer(NotificationEmailer notificationEmailer) {
		return repository.save(notificationEmailer);
	}
	
	public String createNotificationEmailer(NotificationEmailerDTO request) {
	   
	    Merchant merchant = merchantrepository.findById(request.getMerchantId())
	            .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + request.getMerchantId()));

	  
	    NotificationEmailer notificationEmailer = new NotificationEmailer();
	    notificationEmailer.setAppId(request.getAppId());
	    notificationEmailer.setExpressPayFlag(request.getExpressPayFlag());
	    notificationEmailer.setIframePaymentFlag(request.getIframePaymentFlag());
	    notificationEmailer.setMerchantHostedFlag(request.getMerchantHostedFlag());
	    notificationEmailer.setRefundTransactionCustomerEmailFlag(request.getRefundTransactionCustomerEmailFlag());
	    notificationEmailer.setRefundTransactionMerchantEmailFlag(request.getRefundTransactionMerchantEmailFlag());
	    notificationEmailer.setRetryTransactionCustomeFlag(request.getRetryTransactionCustomeFlag());
	    notificationEmailer.setSendMultipleEmailer(request.getSendMultipleEmailer());
	    notificationEmailer.setSurchargeFlag(request.getSurchargeFlag());
	    notificationEmailer.setTransactionAuthenticationEmailFlag(request.getTransactionAuthenticationEmailFlag());
	    notificationEmailer.setTransactionCustomerEmailFlag(request.getTransactionCustomerEmailFlag());
	    notificationEmailer.setTransactionEmailerFlag(request.getTransactionEmailerFlag());
	    notificationEmailer.setTransactionSmsFlag(request.getTransactionSmsFlag());

	    
	    notificationEmailer.setMerchant(merchant);

	    repository.save(notificationEmailer);
	    return "Notification Emailer posted Successfully";
	}

}