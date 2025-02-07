package com.Payvang.Login.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.CustomExceptions.MerchantNotFoundException;
import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.DataAccess.Models.MerchantBank;
import com.Payvang.Login.DataAccess.Models.NotificationEmailer;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Models.AccountStatus;
import com.Payvang.Login.Models.AccountType;
import com.Payvang.Login.Models.MerchantBankRequest;
import com.Payvang.Login.Models.MerchantDTO;
import com.Payvang.Login.Models.MerchantRequest;
import com.Payvang.Login.Models.NotificationEmailerDTO;
import com.Payvang.Login.Repositories.MerchantRepository;
import com.Payvang.Login.Repositories.UserRepository;

import java.util.Optional;

import jakarta.transaction.Transactional;

@Service
public class MerchantService {

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private UserRepository userrepository;

	// Method to create a new merchant
	public void createMerchant(MerchantRequest request) {
		Merchant merchant = new Merchant();
		merchant.setBusinessName(request.getBusinessName());
		merchant.setContactName(request.getContactName());
		merchant.setContactEmail(request.getContactEmail());
		merchant.setContactPhone(request.getContactPhone());
		merchant.setAddress(request.getAddress());
		merchant.setWebsite(request.getWebsite());
		merchant.setCreatedAt(LocalDateTime.now());
		merchant.setUpdatedAt(LocalDateTime.now());

		merchant.setCity(request.getCity());
		merchant.setState(request.getState());
		merchant.setCountry(request.getCountry());
		merchant.setPostalCode(request.getPostalCode());
		merchant.setTelephoneNo(request.getTelephoneNo());
		merchant.setOrganisationType(request.getOrganisationType());
		merchant.setMultiCurrency(request.getMultiCurrency());
		merchant.setOperationAddress(request.getOperationAddress());
		merchant.setOperationCity(request.getOperationCity());
		merchant.setDateOfEstablishment(request.getDateOfEstablishment());
		merchant.setPan(request.getPan());
		merchant.setPanName(request.getPanName());
		merchant.setBusinessModel(request.getBusinessModel());
		merchant.setOperationState(request.getOperationState());
		merchant.setOperationPostalCode(request.getOperationPostalCode());
		merchant.setCin(request.getCin());

		merchantRepository.save(merchant);
	}

	// Get Merchant
	public Merchant getMerchantById(Long merchantId) {
		return merchantRepository.findById(merchantId).orElseThrow(() -> new RuntimeException("Merchant not found"));
	}

	// Method to update existing merchant
	public void updateMerchant(Long merchantId, MerchantRequest request) {
		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		merchant.setBusinessName(request.getBusinessName());
		merchant.setContactName(request.getContactName());
		merchant.setContactEmail(request.getContactEmail());
		merchant.setContactPhone(request.getContactPhone());
		merchant.setAddress(request.getAddress());
		merchant.setWebsite(request.getWebsite());
		merchant.setUpdatedAt(LocalDateTime.now());

		merchantRepository.save(merchant);
	}

	public List<MerchantDTO> getAllMerchants() {
		List<Merchant> merchants = merchantRepository.findAll();

		return merchants.stream().map(merchant -> {
			MerchantDTO dto = new MerchantDTO();
			dto.setMerchantId(merchant.getMerchantId());
			dto.setBusinessName(merchant.getBusinessName());
			dto.setContactEmail(merchant.getContactEmail());
			dto.setContactName(merchant.getContactName());
			dto.setContactPhone(merchant.getContactPhone());
			dto.setWebsite(merchant.getWebsite());
			dto.setCity(merchant.getCity());
			dto.setCountry(merchant.getCountry());
			dto.setPostalCode(merchant.getPostalCode());
			dto.setState(merchant.getState());
			dto.setBusinessModel(merchant.getBusinessModel());
			dto.setOrganisationType(merchant.getOrganisationType());
			dto.setPan(merchant.getPan());
			dto.setPanName(merchant.getPanName());
            dto.setAppId(merchant.getAppId());
			// Convert MerchantBank entities to DTOs
			dto.setBankAccounts(merchant.getBankAccounts().stream().map(bank -> {
				MerchantBankRequest bankDTO = new MerchantBankRequest();
				bankDTO.setAccountHolderName(bank.getAccountHolderName());
				bankDTO.setAccountNumber(bank.getAccountNumber());
				bankDTO.setAccountType(bank.getAccountType().toString());
				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBranchName(bank.getBranchName());
				bankDTO.setIfscCode(bank.getIfscCode());
				bankDTO.setStatus(bank.getStatus().toString());
				return bankDTO;
			}).collect(Collectors.toList()));
			
			if (merchant.getNotificationEmailer() != null) {
				NotificationEmailer emailer = merchant.getNotificationEmailer();
				NotificationEmailerDTO emailerDTO = new NotificationEmailerDTO();
				emailerDTO.setAppId(emailer.getAppId());
				emailerDTO.setExpressPayFlag(emailer.getExpressPayFlag());
				emailerDTO.setIframePaymentFlag(emailer.getIframePaymentFlag());
				emailerDTO.setMerchantHostedFlag(emailer.getMerchantHostedFlag());
				emailerDTO.setRefundTransactionCustomerEmailFlag(emailer.getRefundTransactionCustomerEmailFlag());
				emailerDTO.setRefundTransactionMerchantEmailFlag(emailer.getRefundTransactionMerchantEmailFlag());
				emailerDTO.setRetryTransactionCustomeFlag(emailer.getRetryTransactionCustomeFlag());
				emailerDTO.setSendMultipleEmailer(emailer.getSendMultipleEmailer());
				emailerDTO.setSurchargeFlag(emailer.getSurchargeFlag());
				emailerDTO.setTransactionAuthenticationEmailFlag(emailer.getTransactionAuthenticationEmailFlag());
				emailerDTO.setTransactionCustomerEmailFlag(emailer.getTransactionCustomerEmailFlag());
				emailerDTO.setTransactionEmailerFlag(emailer.getTransactionEmailerFlag());
				emailerDTO.setTransactionSmsFlag(emailer.getTransactionSmsFlag());
				emailerDTO.setMerchantId(merchant.getMerchantId());
				dto.setNotificationEmailer(emailerDTO);
			}

			return dto;
		}).collect(Collectors.toList());
	}

	public MerchantDTO getMerchantDetailById(String appId) {

		User user = userrepository.findByAppId(appId).orElseThrow(() -> new RuntimeException("User not found"));

		Merchant merchant = merchantRepository.findByAppId(appId)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		MerchantDTO dto = new MerchantDTO();
		dto.setMerchantId(merchant.getMerchantId());
		dto.setBusinessName(merchant.getBusinessName());
		dto.setContactEmail(merchant.getContactEmail());
		dto.setContactName(merchant.getContactName());
		dto.setContactPhone(merchant.getContactPhone());
		dto.setWebsite(merchant.getWebsite());
		dto.setCity(merchant.getCity());
		dto.setCountry(merchant.getCountry());
		dto.setPostalCode(merchant.getPostalCode());
		dto.setState(merchant.getState());
		dto.setBusinessModel(merchant.getBusinessModel());
		dto.setOrganisationType(merchant.getOrganisationType());
		dto.setPan(merchant.getPan());
		dto.setPanName(merchant.getPanName());
		dto.setAddress(merchant.getAddress());
		dto.setTelephoneNo(merchant.getTelephoneNo());
		dto.setMultiCurrency(merchant.getMultiCurrency());
		dto.setOperationAddress(merchant.getOperationAddress());
		dto.setOperationCity(merchant.getOperationCity());
		dto.setDateOfEstablishment(merchant.getDateOfEstablishment());
		dto.setOperationState(merchant.getOperationState());
		dto.setOperationPostalCode(merchant.getOperationPostalCode());
		dto.setCin(merchant.getCin());
		dto.setAppId(merchant.getAppId());

		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		// Convert MerchantBank entities to DTOs
		dto.setBankAccounts(merchant.getBankAccounts().stream().map(bank -> {
			MerchantBankRequest bankDTO = new MerchantBankRequest();
			bankDTO.setMerchantId(merchant.getMerchantId());
			bankDTO.setAccountHolderName(bank.getAccountHolderName());
			bankDTO.setAccountNumber(bank.getAccountNumber());
			bankDTO.setAccountType(bank.getAccountType().toString());
			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBranchName(bank.getBranchName());
			bankDTO.setIfscCode(bank.getIfscCode());
			bankDTO.setStatus(bank.getStatus().toString());
			return bankDTO;
		}).collect(Collectors.toList()));

		if (merchant.getNotificationEmailer() != null) {
			NotificationEmailer emailer = merchant.getNotificationEmailer();
			NotificationEmailerDTO emailerDTO = new NotificationEmailerDTO();
			emailerDTO.setAppId(emailer.getAppId());
			emailerDTO.setExpressPayFlag(emailer.getExpressPayFlag());
			emailerDTO.setIframePaymentFlag(emailer.getIframePaymentFlag());
			emailerDTO.setMerchantHostedFlag(emailer.getMerchantHostedFlag());
			emailerDTO.setRefundTransactionCustomerEmailFlag(emailer.getRefundTransactionCustomerEmailFlag());
			emailerDTO.setRefundTransactionMerchantEmailFlag(emailer.getRefundTransactionMerchantEmailFlag());
			emailerDTO.setRetryTransactionCustomeFlag(emailer.getRetryTransactionCustomeFlag());
			emailerDTO.setSendMultipleEmailer(emailer.getSendMultipleEmailer());
			emailerDTO.setSurchargeFlag(emailer.getSurchargeFlag());
			emailerDTO.setTransactionAuthenticationEmailFlag(emailer.getTransactionAuthenticationEmailFlag());
			emailerDTO.setTransactionCustomerEmailFlag(emailer.getTransactionCustomerEmailFlag());
			emailerDTO.setTransactionEmailerFlag(emailer.getTransactionEmailerFlag());
			emailerDTO.setTransactionSmsFlag(emailer.getTransactionSmsFlag());
			emailerDTO.setMerchantId(merchant.getMerchantId());
			dto.setNotificationEmailer(emailerDTO);
		}

		return dto;
	}

	@Transactional
	public Merchant updateMerchant(Long id, MerchantDTO updates) throws MerchantNotFoundException {

		Merchant existingMerchant = merchantRepository.findById(id)
				.orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id " + id));

		Optional.ofNullable(updates.getBusinessName()).ifPresent(existingMerchant::setBusinessName);
		Optional.ofNullable(updates.getContactName()).ifPresent(existingMerchant::setContactName);
		Optional.ofNullable(updates.getContactPhone()).ifPresent(existingMerchant::setContactPhone);
		Optional.ofNullable(updates.getAddress()).ifPresent(existingMerchant::setAddress);
		Optional.ofNullable(updates.getCity()).ifPresent(existingMerchant::setCity);
		Optional.ofNullable(updates.getState()).ifPresent(existingMerchant::setState);
		Optional.ofNullable(updates.getCountry()).ifPresent(existingMerchant::setCountry);
		Optional.ofNullable(updates.getPostalCode()).ifPresent(existingMerchant::setPostalCode);
		Optional.ofNullable(updates.getTelephoneNo()).ifPresent(existingMerchant::setTelephoneNo);
		Optional.ofNullable(updates.getWebsite()).ifPresent(existingMerchant::setWebsite);
		Optional.ofNullable(updates.getOrganisationType()).ifPresent(existingMerchant::setOrganisationType);
		Optional.ofNullable(updates.getMultiCurrency()).ifPresent(existingMerchant::setMultiCurrency);
		Optional.ofNullable(updates.getOperationAddress()).ifPresent(existingMerchant::setOperationAddress);
		Optional.ofNullable(updates.getOperationCity()).ifPresent(existingMerchant::setOperationCity);
		Optional.ofNullable(updates.getDateOfEstablishment()).ifPresent(existingMerchant::setDateOfEstablishment);
		Optional.ofNullable(updates.getPan()).ifPresent(existingMerchant::setPan);
		Optional.ofNullable(updates.getPanName()).ifPresent(existingMerchant::setPanName);
		Optional.ofNullable(updates.getBusinessModel()).ifPresent(existingMerchant::setBusinessModel);
		Optional.ofNullable(updates.getOperationState()).ifPresent(existingMerchant::setOperationState);
		Optional.ofNullable(updates.getOperationPostalCode()).ifPresent(existingMerchant::setOperationPostalCode);
		Optional.ofNullable(updates.getCin()).ifPresent(existingMerchant::setCin);

		if (updates.getBankAccounts() != null && !updates.getBankAccounts().isEmpty()) {
			List<MerchantBank> existingBanks = existingMerchant.getBankAccounts();

			for (MerchantBankRequest bankUpdate : updates.getBankAccounts()) {
				Optional<MerchantBank> existingBankOpt = existingBanks.stream()
						.filter(b -> b.getMerchant().getMerchantId().equals(id)).findFirst();

				if (existingBankOpt.isPresent()) {

					MerchantBank bank = existingBankOpt.get();
					Optional.ofNullable(bankUpdate.getAccountHolderName()).ifPresent(bank::setAccountHolderName);
					Optional.ofNullable(bankUpdate.getAccountNumber()).ifPresent(bank::setAccountNumber);
					Optional.ofNullable(bankUpdate.getBankName()).ifPresent(bank::setBankName);
					Optional.ofNullable(bankUpdate.getBranchName()).ifPresent(bank::setBranchName);
					Optional.ofNullable(bankUpdate.getAccountType())
							.ifPresent(type -> bank.setAccountType(AccountType.valueOf(type.toUpperCase())));
					Optional.ofNullable(bankUpdate.getStatus())
							.ifPresent(type -> bank.setStatus(AccountStatus.valueOf(type.toUpperCase())));
					Optional.ofNullable(bankUpdate.getIfscCode()).ifPresent(bank::setIfscCode);
				} else {

					MerchantBank newBank = new MerchantBank();
					newBank.setAccountNumber(bankUpdate.getAccountNumber());
					newBank.setIfscCode(bankUpdate.getIfscCode());
					Optional.ofNullable(bankUpdate.getAccountHolderName()).ifPresent(newBank::setAccountHolderName);
					Optional.ofNullable(bankUpdate.getBankName()).ifPresent(newBank::setBankName);
					Optional.ofNullable(bankUpdate.getBranchName()).ifPresent(newBank::setBranchName);
					Optional.ofNullable(bankUpdate.getAccountType())
							.ifPresent(type -> newBank.setAccountType(AccountType.valueOf(type)));
					Optional.ofNullable(bankUpdate.getStatus())
							.ifPresent(type -> newBank.setStatus(AccountStatus.valueOf(type.toUpperCase())));
					newBank.setMerchant(existingMerchant);
					existingBanks.add(newBank);
				}
			}
			existingMerchant.setBankAccounts(existingBanks);
		}

		if (updates.getNotificationEmailer() != null) {
			NotificationEmailerDTO emailerUpdate = updates.getNotificationEmailer();
			NotificationEmailer emailer = existingMerchant.getNotificationEmailer();

			if (emailer == null) {
				emailer = new NotificationEmailer();
				emailer.setMerchant(existingMerchant);
			}

			Optional.ofNullable(emailerUpdate.getAppId()).ifPresent(emailer::setAppId);
			Optional.ofNullable(emailerUpdate.getExpressPayFlag()).ifPresent(emailer::setExpressPayFlag);
			Optional.ofNullable(emailerUpdate.getIframePaymentFlag()).ifPresent(emailer::setIframePaymentFlag);
			Optional.ofNullable(emailerUpdate.getMerchantHostedFlag()).ifPresent(emailer::setMerchantHostedFlag);
			Optional.ofNullable(emailerUpdate.getRefundTransactionCustomerEmailFlag())
					.ifPresent(emailer::setRefundTransactionCustomerEmailFlag);
			Optional.ofNullable(emailerUpdate.getRefundTransactionMerchantEmailFlag())
					.ifPresent(emailer::setRefundTransactionMerchantEmailFlag);
			Optional.ofNullable(emailerUpdate.getRetryTransactionCustomeFlag())
					.ifPresent(emailer::setRetryTransactionCustomeFlag);
			Optional.ofNullable(emailerUpdate.getSendMultipleEmailer()).ifPresent(emailer::setSendMultipleEmailer);
			Optional.ofNullable(emailerUpdate.getSurchargeFlag()).ifPresent(emailer::setSurchargeFlag);
			Optional.ofNullable(emailerUpdate.getTransactionAuthenticationEmailFlag())
					.ifPresent(emailer::setTransactionAuthenticationEmailFlag);
			Optional.ofNullable(emailerUpdate.getTransactionCustomerEmailFlag())
					.ifPresent(emailer::setTransactionCustomerEmailFlag);
			Optional.ofNullable(emailerUpdate.getTransactionEmailerFlag())
					.ifPresent(emailer::setTransactionEmailerFlag);
			Optional.ofNullable(emailerUpdate.getTransactionSmsFlag()).ifPresent(emailer::setTransactionSmsFlag);

			existingMerchant.setNotificationEmailer(emailer);
		}

		return merchantRepository.save(existingMerchant);
	}

}
