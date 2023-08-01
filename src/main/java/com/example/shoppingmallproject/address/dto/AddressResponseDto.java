package com.example.shoppingmallproject.address.dto;

import com.example.shoppingmallproject.address.entity.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddressResponseDto {
    private String zipcode;
    private String address;
    @QueryProjection
    public AddressResponseDto(String zipcode, String address) {
        this.zipcode = zipcode;
        this.address = address;
    }

    private AddressResponseDto(Address address) {
        this.zipcode = address.getZipCode();
        this.address = address.getUserAddress();
    }

    public AddressResponseDto of(Address address){
        return new AddressResponseDto(address);
    }
}
