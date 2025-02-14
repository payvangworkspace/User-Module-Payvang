package com.Payvang.Login.Models;

import java.math.BigInteger;
import java.util.List;

import com.Payvang.Login.DataAccess.Models.LoginHistory;

public class LoginHistoryResponse {
public BigInteger recordsTotal;
public BigInteger recordsFiltered;
public List<LoginHistory> aaData;



public LoginHistoryResponse(BigInteger recordsTotal, BigInteger recordsFiltered, List<LoginHistory> aaData) {
    this.recordsTotal = recordsTotal;
    this.recordsFiltered = recordsFiltered;
    this.aaData = aaData;
}
}