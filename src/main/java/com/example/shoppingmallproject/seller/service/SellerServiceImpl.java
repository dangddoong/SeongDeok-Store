package com.example.shoppingmallproject.seller.service;

import com.example.shoppingmallproject.seller.dto.SellerRequestDto;
import com.example.shoppingmallproject.seller.entity.Seller;
import com.example.shoppingmallproject.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void sellerSignUp(SellerRequestDto dto){
        String email = dto.getEmail();
        String password = passwordEncoder.encode(dto.getPassword());
        String phone = dto.getPhone();
        String name = dto.getName();

        Seller seller = Seller.builder()
                .email(email)
                .name(name)
                .password(password)
                .phone(phone)
                .build();

        Optional<Seller> byEmail = sellerRepository.findByEmail(email);

        if (byEmail.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }

        sellerRepository.save(seller);
    }
}
