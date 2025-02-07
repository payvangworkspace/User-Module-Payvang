package com.Payvang.Login.Repositories;

import com.Payvang.Login.DataAccess.Models.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
    // Additional custom queries if needed
}