package com.example.shoppingmallproject.user.entity;

import com.example.shoppingmallproject.address.repository.AddressRepository;
import com.example.shoppingmallproject.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Test
    public void oneToOneTest(){

    }
}