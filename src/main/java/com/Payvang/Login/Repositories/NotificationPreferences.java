package com.Payvang.Login.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.MerchantNotificationPreferences;

@Repository
public interface NotificationPreferences extends CrudRepository<MerchantNotificationPreferences, Integer> {

	Optional<MerchantNotificationPreferences>findByMerchantId(int merchantId);
	
	
	
}
