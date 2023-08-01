package com.example.shoppingmallproject.payment.repository.query;

import com.example.shoppingmallproject.payment.entity.Payment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentQueryRepositoryImpl implements PaymentQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Payment> findPaymentsByUserId(Long userId) {
        return null;
    }
}
