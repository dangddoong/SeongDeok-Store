package com.example.shoppingmallproject.seller.dto;

import com.example.shoppingmallproject.seller.entity.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;

    private SellerResponseDto(Seller seller) {
        this.id = seller.getId();
        this.name = seller.getName();
        this.email = seller.getEmail();
        this.phone = seller.getPhone();
    }

    public static SellerResponseDto of(Seller seller){
        return new SellerResponseDto(seller);
    }
}
