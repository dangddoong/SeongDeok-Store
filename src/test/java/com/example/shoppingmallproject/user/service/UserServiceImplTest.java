package com.example.shoppingmallproject.user.service;

import com.example.shoppingmallproject.user.dto.SignUpRequestDto;
import com.example.shoppingmallproject.user.dto.UserResponseDto;
import com.example.shoppingmallproject.user.entity.User;
import com.example.shoppingmallproject.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    private SignUpRequestDto requestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUser();
    }

    void setUser(){
        user = User.builder()
                .username("testuser")
                .phone("123456789")
                .email("test@example.com")
                .password(passwordEncoder.encode("password"))
                .build();

        requestDto = new SignUpRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password");
        requestDto.setUsername("testuser");
        requestDto.setPhone("123456789");
    }

    @Test
    void getUserObjectById_ExistingUser_ReturnsUserResponseDto() {
        // Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        UserResponseDto result = userService.getUserById(userId);

        // Then
        UserResponseDto expectedDto = UserResponseDto.of(user);

        Assertions.assertEquals(expectedDto.getEmail(), result.getEmail());
        // 생성되는 시점이 달라 static하게 안됩니다 ㅠ 해결하기엔 귀찮음.

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_NonExistingUser_ThrowsNoSuchElementException() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void userSignUp_ValidUserRequestDto_UserSaved() {
        // Given - 세팅해놓은 requestDto

        when(userRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");

        // When
        userService.signUp(requestDto);

        // Then
        verify(userRepository, times(1)).existsByEmail(requestDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void userSignUp_DuplicateUser_ThrowsIllegalArgumentException() {
        // Given - 세팅해놓은 requestDto

        when(userRepository.existsByEmail(requestDto.getEmail())).thenReturn(true);

        // When/Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.signUp(requestDto));
        verify(userRepository, times(1)).existsByEmail(requestDto.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

}
