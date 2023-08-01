package com.example.shoppingmallproject.payment.repository;

import com.example.shoppingmallproject.payment.entity.Payment;
import com.example.shoppingmallproject.payment.repository.query.PaymentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentQueryRepository {

}
