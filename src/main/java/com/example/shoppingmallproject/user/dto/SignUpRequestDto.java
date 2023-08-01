package com.example.shoppingmallproject.user.dto;

import com.example.shoppingmallproject.user.entity.User;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor
@Setter
public class SignUpRequestDto {

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
        message = "이메일 형식을 확인해주세요")
    private String email;
    @Size(min = 4, max = 10)
    private String password;
    @Size(min = 4, max = 10)
    private String username;
    private String phone;

    @Builder
    public SignUpRequestDto(String email, String password, String username, String phone) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
    }

    public User toEntity(String encodingPassword) {
        return User.builder()
            .email(this.email)
            .password(encodingPassword)
            .username(this.username)
            .phone(this.phone)
            .build();
    }
}
