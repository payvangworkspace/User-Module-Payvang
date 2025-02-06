package com.Payvang.Login.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Payvang.Login.DataAccess.Models.WhiteLableBranding;

@Repository
public interface WhiteLableBrandingRepo extends CrudRepository<WhiteLableBranding, Long>{

	WhiteLableBranding findByAppId(String appId);

}
