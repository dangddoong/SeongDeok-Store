package com.example.shoppingmallproject.payment.repository.query;

import com.example.shoppingmallproject.payment.entity.Payment;

import java.util.List;

public interface PaymentQueryRepository {
    List<Payment> findPaymentsByUserId(Long userId);
}
