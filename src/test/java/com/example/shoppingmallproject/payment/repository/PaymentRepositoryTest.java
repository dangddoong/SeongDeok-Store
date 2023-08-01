package com.example.shoppingmallproject.payment.repository;

import com.example.shoppingmallproject.payment.entity.Payment;
import com.example.shoppingmallproject.user.entity.User;
import com.example.shoppingmallproject.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository  userRepository;
    @Test
    public void createPayment(){
        //given
        User user = new User("123","123@gamil.com","1234","123-123-123");
        userRepository.save(user);
        final Payment payment = Payment.builder()
                .users(user)
                .totalPrice(123L)
            .payNumber(Long.valueOf(1000))
            .payMethod("카드")
            .build();

        //when
        final Payment result = paymentRepository.save(payment);

        //then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getPayMethod()).isEqualTo("카드");
        assertThat(result.getPayNumber()).isEqualTo(1000);
    }
}