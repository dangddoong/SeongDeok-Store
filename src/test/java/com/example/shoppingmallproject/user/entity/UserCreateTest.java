package com.example.shoppingmallproject.user.entity;

import com.example.shoppingmallproject.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class UserCreateTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("user 생성 테스트")
    void UserCreate(){

        //given
        User user1 = User.builder()
            .username("asdf")
            .phone("01234")
            .email("asdf@gmail.com")
            .password("asdf")
            .build();

        //when
        User savedUser = userRepository.save(user1);

        //then
        System.out.println(savedUser.getCreatedAt());
        System.out.println(savedUser.getPhone());
        Assertions.assertEquals(savedUser.getEmail(),"asdf@gmail.com");

    }

}