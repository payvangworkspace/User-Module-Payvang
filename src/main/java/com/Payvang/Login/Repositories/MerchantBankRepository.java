package com.Payvang.Login.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.Merchant;
import com.Payvang.Login.DataAccess.Models.MerchantBank;

@Repository
public interface MerchantBankRepository extends JpaRepository<MerchantBank,Long> {

	

}
