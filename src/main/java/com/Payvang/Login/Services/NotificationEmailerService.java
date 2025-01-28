package com.Payvang.Login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.DataAccess.Models.NotificationEmailer;
import com.Payvang.Login.Repositories.NotificationEmailerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationEmailerService {

	private final NotificationEmailerRepository repository;

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

}