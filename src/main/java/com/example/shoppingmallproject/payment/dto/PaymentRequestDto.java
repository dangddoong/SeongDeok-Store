package com.example.shoppingmallproject.payment.dto;

import lombok.Getter;

@Getter
public class PaymentRequestDto {
    private String payMethod;
    private String payNumber;
}
