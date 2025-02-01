package com.Payvang.Login.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.DataAccess.Models.MerchantBank;
import com.Payvang.Login.Models.AccountStatus;
import com.Payvang.Login.Models.AccountType;
import com.Payvang.Login.Models.MerchantBankRequest;
import com.Payvang.Login.Repositories.MerchantBankRepository;
import com.Payvang.Login.Repositories.MerchantRepository;

@Service
public class MerchantBankService {

    @Autowired
    private MerchantBankRepository merchantBankRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    public void saveMerchantBankDetails(MerchantBankRequest request) {
        MerchantBank merchantBank = new MerchantBank();
        
        
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
        merchantBank.setMerchant(merchant);
        
        merchantBank.setAccountHolderName(request.getAccountHolderName());
        merchantBank.setBankName(request.getBankName());
        merchantBank.setBranchName(request.getBranchName());
        merchantBank.setAccountNumber(request.getAccountNumber());
        merchantBank.setIfscCode(request.getIfscCode());
        merchantBank.setAccountType(AccountType.valueOf(request.getAccountType().toUpperCase()));
        merchantBank.setStatus(AccountStatus.valueOf(request.getStatus().toUpperCase()));
        merchantBank.setCreatedAt(LocalDateTime.now());
        merchantBank.setUpdatedAt(LocalDateTime.now());

        
        merchantBankRepository.save(merchantBank);
    }
    
    public MerchantBank getMerchantBankById(Long merchantId) {
        return merchantBankRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
    }

   
    public void updateMerchantBank(Long accountId, MerchantBankRequest request) {
    	System.out.println("accountid in merchant "+accountId);
        MerchantBank merchantBank = merchantBankRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Merchant Bank account not found"));

        merchantBank.setAccountHolderName(request.getAccountHolderName());
        merchantBank.setBankName(request.getBankName());
        merchantBank.setBranchName(request.getBranchName());
        merchantBank.setAccountNumber(request.getAccountNumber());
        merchantBank.setIfscCode(request.getIfscCode());
        merchantBank.setAccountType(AccountType.valueOf(request.getAccountType()));
        merchantBank.setStatus(AccountStatus.valueOf(request.getStatus()));
        merchantBank.setUpdatedAt(LocalDateTime.now());

        merchantBankRepository.save(merchantBank);
    }
    
}
