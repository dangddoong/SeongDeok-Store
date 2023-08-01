package com.example.shoppingmallproject.address.service;

import com.example.shoppingmallproject.address.dto.AddressRequestDto;
import com.example.shoppingmallproject.address.dto.AddressResponseDto;
import com.example.shoppingmallproject.address.entity.Address;
import com.example.shoppingmallproject.address.repository.AddressRepository;
import com.example.shoppingmallproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AddressResponseDto> getMyAddresses(User user) {
        return addressRepository.getMyAddresses(user);
    }

    @Transactional
    @Override
    public Long createAddress(User user, AddressRequestDto dto) {
        // 1. 유저가 이미 해당 어드레스를 가지고 있는지 확인하는 로직
        if (addressRepository.existsByUserId(user.getId(), dto.getAddress())) {
            throw new IllegalArgumentException("이미 추가되어 있는 주소입니다.");
        }

        Address address = Address.builder()
                .userAddress(dto.getAddress())
                .zipCode(dto.getZipcode())
                .user(user)
                .build();

        addressRepository.save(address);

        return address.getId();
    }

    @Transactional
    @Override
    public void deleteAddress(User user, Long addressId) {
        Address address = addressRepository.findAddressByUserAndId(user, addressId).orElseThrow(
                () -> new NoSuchElementException("잘못된 접근입니다.")
        );

        addressRepository.delete(address);
    }
}
