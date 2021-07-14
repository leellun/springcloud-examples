package com.newland.payment.service;

import com.newland.payment.domain.Response;

import java.math.BigDecimal;

public interface IPaymentService {
    public Response payment(String userAccount, BigDecimal cost, String requestKey);
}
