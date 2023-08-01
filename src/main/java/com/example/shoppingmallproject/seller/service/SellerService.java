package com.example.shoppingmallproject.seller.service;

import com.example.shoppingmallproject.seller.dto.SellerRequestDto;
import org.springframework.transaction.annotation.Transactional;

public interface SellerService {
    @Transactional
    void sellerSignUp(SellerRequestDto dto);
}
