package com.example.shoppingmallproject.address.controller;

import com.example.shoppingmallproject.address.dto.AddressRequestDto;
import com.example.shoppingmallproject.address.dto.AddressResponseDto;
import com.example.shoppingmallproject.address.service.AddressService;
import com.example.shoppingmallproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/me")
    public List<AddressResponseDto> getMyAddresses(@RequestAttribute("user") User user) {
        return addressService.getMyAddresses(user);
    }

    @PostMapping
    public ResponseEntity<Long> createAddress(@RequestBody AddressRequestDto dto, @AuthenticationPrincipal User user) {
        Long addressId = addressService.createAddress(user, dto);
        return new ResponseEntity<>(addressId, HttpStatus.CREATED);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId, @AuthenticationPrincipal User user) {
        addressService.deleteAddress(user, addressId);
        return new ResponseEntity<>("Address deleted successfully.", HttpStatus.OK);
    }
}
