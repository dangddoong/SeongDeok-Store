package com.example.shoppingmallproject.user.dto;

import com.example.shoppingmallproject.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private String phone;

    @Builder
    private UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.phone = user.getPhone();
    }

    public static UserResponseDto of (User user) {
        return new UserResponseDto(user);
    }
}
