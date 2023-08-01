package com.example.shoppingmallproject.address.service;

import com.example.shoppingmallproject.address.dto.AddressRequestDto;
import com.example.shoppingmallproject.address.dto.AddressResponseDto;
import com.example.shoppingmallproject.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressService {
    @Transactional(readOnly = true)
    List<AddressResponseDto> getMyAddresses(User user);

    @Transactional
    Long createAddress(User user, AddressRequestDto dto);

    @Transactional
    void deleteAddress(User user, Long addressId);
}
