package com.newland.payment.domain;

import java.math.BigDecimal;

public class Account {
    private String account;
    private BigDecimal balance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
