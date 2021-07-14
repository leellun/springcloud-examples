package com.newland.payment.mapper;

import com.newland.payment.domain.Payment;

public interface PaymentMapper {
    void insert(Payment payment);
    Payment selectPayment(String key);
}
