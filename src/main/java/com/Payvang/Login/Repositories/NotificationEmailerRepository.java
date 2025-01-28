package com.Payvang.Login.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.NotificationEmailer;

@Repository
public interface NotificationEmailerRepository extends JpaRepository<NotificationEmailer, Long> {

	NotificationEmailer findByAppId(String appId);

}
