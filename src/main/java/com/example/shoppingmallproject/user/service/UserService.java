package com.example.shoppingmallproject.user.service;

import com.example.shoppingmallproject.user.dto.SignInRequestDto;
import com.example.shoppingmallproject.user.dto.SignUpRequestDto;
import com.example.shoppingmallproject.user.dto.TokenResponseDto;
import com.example.shoppingmallproject.user.dto.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserService  {
    UserResponseDto getUserById(Long userId);
    UserResponseDto signUp(SignUpRequestDto signUpRequestDto);
    TokenResponseDto signIn(SignInRequestDto signInRequestDto, String browser) throws JsonProcessingException;
    void signOut(String email);
    TokenResponseDto reissue(String refreshToken, String browser) throws JsonProcessingException;
}

