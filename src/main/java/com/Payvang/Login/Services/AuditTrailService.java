package com.Payvang.Login.Services;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.DataAccess.Models.AuditTrail;
import com.Payvang.Login.Repositories.AuditTrailRepository;



@Service
public class AuditTrailService {
	
	
	private Logger logger=LoggerFactory.getLogger(AuditTrailService.class);

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public void logAuditTrail(String entityName, String action, String performedByUser,
                              String oldValue, String newValue, String ipAddress, String comments) {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntityName(entityName);
   
        auditTrail.setAction(action);
        auditTrail.setPerformedByUser(performedByUser);
        auditTrail.setPerformedAt(LocalDateTime.now());
        auditTrail.setOldValue(oldValue);
        auditTrail.setNewValue(newValue);
        auditTrail.setIpAddress(ipAddress);
        auditTrail.setComments(comments);

        auditTrailRepository.save(auditTrail);
    }
    
    
    public void saveAuditTrail(AuditTrail auditTrail) {
    	
    	auditTrailRepository.save(auditTrail);
    	logger.info("Audit Trails saved successfully..");
    	
    }
    
    
    
    
    
}