package com.example.shoppingmallproject.payment.service;

import com.example.shoppingmallproject.address.entity.Address;
import com.example.shoppingmallproject.payment.entity.Payment;
import com.example.shoppingmallproject.payment.repository.PaymentRepository;
import com.example.shoppingmallproject.user.dto.SignUpRequestDto;
import com.example.shoppingmallproject.user.entity.User;
import com.example.shoppingmallproject.user.repository.UserRepository;
import com.example.shoppingmallproject.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedHashSet;

@SpringBootTest
class PaymentServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @InjectMocks
    private PaymentServiceImpl paymentService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private Payment payment;

    private SignUpRequestDto requestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUser();
    }

    void setUser(){
        String username = "asdf";
        String password = "asdf";
        String phone = "1234";
        LinkedHashSet<Address> addresses = new LinkedHashSet<Address>();
        String email = "asdf@gmail.com";
        String address = "광명시";
        String zipCode = "우편번호";

        user = User.builder()
            .username(username)
            .phone(phone)
            .email(email)
            .password(passwordEncoder.encode(password))
            .build();


        requestDto = new SignUpRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password");
        requestDto.setUsername("testuser");
        requestDto.setPhone("123456789");
    }

    @Test
    void paymentServiceImplTest(){



    }

}