package com.Payvang.Login.DataAccess.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "login_history")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String browser;

    private String businessName;

    private String emailId;

    private String failureReason;

    private String ip;

    private String os;

    private boolean status;

    private String timeStamp;
}

