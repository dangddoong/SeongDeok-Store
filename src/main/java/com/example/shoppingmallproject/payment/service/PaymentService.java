package com.example.shoppingmallproject.payment.service;

import com.example.shoppingmallproject.payment.dto.PaymentRequestDto;
import com.example.shoppingmallproject.payment.dto.PaymentResponseDto;
import com.example.shoppingmallproject.user.entity.User;

import java.util.List;

public interface PaymentService {

    void pay(PaymentRequestDto paymentRequestDto);

    List<PaymentResponseDto> getPayments(User user);

    List<PaymentResponseDto> doCartsPayments(User user);
}
