//package com.example.shoppingmallproject.user.entity;
//
//import com.example.shoppingmallproject.address.entity.Address;
//import com.example.shoppingmallproject.user.repository.UserRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.LinkedHashSet;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@SpringBootTest
//class UserAddressTest {
//
//    @Autowired
//    UserRepository usersRepository;
//
//    @DisplayName("ALL 결과 테스트")
//    @Test
//    void cascadeTypeAll(){
//        String username = "asdf";
//        String password = "asdf";
//        String phone = "1234";
//        LinkedHashSet<Address> addresses = new LinkedHashSet<Address>();
//        String email = "asdf@gmail.com";
//        String address = "광명시";
//        String zipCode = "우편번호";
//
//        Address address1 = Address.builder()
//                .userAddress(address)
//                .zipCode(zipCode)
//                .build();
//
//        addresses.add(address1);
//
//        System.out.println(address1.getUserAddress());
//
//        User user1 = User.builder()
//            .phone(phone)
//            .email(email)
//            .password(password)
//            .username(username)
//            .build();
//
//        System.out.println(user1.getId());
//
//
//        usersRepository.save(user1);
//
//        User user3 = usersRepository.findById(1L).orElseThrow(
//            () -> new NoSuchElementException("유저 정보가 없습니다.")
//        );
//
//        System.out.println(user3.getId());
////        System.out.println(user2.getId());
//
//        Assertions.assertEquals(user3.getId(), 1L);
//
//
////        usersRepository.save(user1);
////
////        User user2 = usersRepository.findById(1L).get();
////
//
//
//    }
//
//}