package com.example.shoppingmallproject.payment.controller;

import com.example.shoppingmallproject.payment.dto.PaymentResponseDto;
import com.example.shoppingmallproject.payment.service.PaymentService;
import com.example.shoppingmallproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/payments") // 결제내역 확인
    public List<PaymentResponseDto> getPayments(@AuthenticationPrincipal User user){
        return paymentService.getPayments(user);
    }

    @PostMapping("/carts/payments") //장바구니에서 결제
    public List<PaymentResponseDto> doCartsPayments(@AuthenticationPrincipal User user){
        return paymentService.doCartsPayments(user);
    }

//    @PostMapping("/products/{productsId}/payments") //상품에서 결제
//    public PaymentsResultDto doProductPayments(@PathVariable Long productsId, @AuthenticationPrincipal User user){
//        return paymentService.doProductPayments(user);
//    }
}
