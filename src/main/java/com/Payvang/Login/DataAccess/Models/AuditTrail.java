package com.Payvang.Login.DataAccess.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityName; // Name of the entity being audited (e.g., "WhiteLableBranding")

    @Column(nullable=false)
    private LocalDateTime updatedat;
   
    
    @Column(nullable = false)
    private String action; // Action performed (e.g., "CREATE", "UPDATE", "DELETE")

    @Column(nullable = false)
    private String performedByUser; // Who performed the action (username, admin, etc.)

    @Column(nullable = false)
    private LocalDateTime performedAt; // Timestamp of the action

    @Lob
    private String oldValue; // JSON or string representation of the old state

    @Lob
    private String newValue; // JSON or string representation of the new state

    private String ipAddress; // Optional: IP address of the user/system performing the action

    private String comments; // Additional details or comments
}