package com.example.shoppingmallproject.seller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
}
