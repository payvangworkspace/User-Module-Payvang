package com.Payvang.Login.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Long> {

	Optional<Merchant> findByAppId(String merchantId);

}
