package com.example.shoppingmallproject.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class TokenResponseDto {

  private final String accessToken;
  private final String refreshToken;

  private TokenResponseDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static TokenResponseDto of (String accessToken, String refreshToken){
    return new TokenResponseDto(accessToken, refreshToken);
  }
}
