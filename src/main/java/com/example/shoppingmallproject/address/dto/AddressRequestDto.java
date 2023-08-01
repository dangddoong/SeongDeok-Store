package com.example.shoppingmallproject.address.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddressRequestDto {
    private String zipcode;
    private String address;
}
