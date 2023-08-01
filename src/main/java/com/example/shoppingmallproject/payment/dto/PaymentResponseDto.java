package com.example.shoppingmallproject.payment.dto;

import com.example.shoppingmallproject.payment.entity.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long totalPrice;
    private String payMethod;
    private Long payNumber;

    private PaymentResponseDto(Payment payment) {
        this.id = payment.getId();
        this.totalPrice = payment.getTotalPrice();
        this.payMethod = payment.getPayMethod();
        this.payNumber = payment.getPayNumber();
    }

    public static PaymentResponseDto of(Payment payment){
        return new PaymentResponseDto(payment);
    }
}
