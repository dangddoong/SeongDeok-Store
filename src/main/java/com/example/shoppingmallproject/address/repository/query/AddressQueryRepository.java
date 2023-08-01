package com.example.shoppingmallproject.address.repository.query;

import com.example.shoppingmallproject.address.dto.AddressResponseDto;
import com.example.shoppingmallproject.address.entity.Address;
import com.example.shoppingmallproject.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface AddressQueryRepository {
    List<AddressResponseDto> getMyAddresses(User user);

    boolean existsByUserId(Long userId, String addressArg);

    Optional<Address> findAddressByUserAndId(User user, Long addressId);
}
