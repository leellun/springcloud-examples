package com.newland.payment.domain;

import java.math.BigDecimal;

public class Payment {
    private String userAccount;
    private BigDecimal cost;
    private String requestKey;

    public Payment(String userAccount, BigDecimal cost, String requestKey) {
        this.userAccount = userAccount;
        this.cost = cost;
        this.requestKey = requestKey;
    }
}
