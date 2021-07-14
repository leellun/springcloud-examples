package com.newland.payment.mapper;

import com.newland.payment.domain.Account;

public interface AccountMapper {
    public Account selectUser(String account);

    public void updateAccount(Account account);
}
