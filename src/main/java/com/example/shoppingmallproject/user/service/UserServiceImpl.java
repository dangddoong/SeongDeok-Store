package com.example.shoppingmallproject.user.service;

import com.example.shoppingmallproject.common.redis.RedisAuthDAO;
import com.example.shoppingmallproject.common.redis.RedisDAO;
import com.example.shoppingmallproject.common.security.jwt.JwtUtil;
import com.example.shoppingmallproject.user.dto.SignInRequestDto;
import com.example.shoppingmallproject.user.dto.SignUpRequestDto;
import com.example.shoppingmallproject.user.dto.TokenResponseDto;
import com.example.shoppingmallproject.user.dto.UserResponseDto;
import com.example.shoppingmallproject.user.entity.User;
import com.example.shoppingmallproject.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisDAO redisDAO;
    private final JwtUtil jwtUtil;
    private final RedisAuthDAO redisAuthDAO;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId){
        return UserResponseDto.of(userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("아이디에 해당하는 유저가 없습니다.")
        ));
    }
    @Override
    @Transactional
    public UserResponseDto signUp(SignUpRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        String encodingPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = requestDto.toEntity(encodingPassword);
        userRepository.save(user);
        return UserResponseDto.of(user);
//        return user.getId(); // 생성되는 유저의 id를 반환해서, Controller 단에서 생성된 유저의 URI 를 정확히 참조하도록 함.
    }


    @Override
    @Transactional
    public TokenResponseDto signIn(SignInRequestDto signInRequestDto, String browser) throws JsonProcessingException {
        // 보안을 위해 아이디&비밀번호 관련 응답은 통일되게 설정하였음.
        User user = userRepository.findByEmail(signInRequestDto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("아이디와 비밀번호를 확인해주세요"));
        if(!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("아이디와 비밀번호를 확인해주세요");
        }
        String accessToken = jwtUtil.createToken(user.getEmail(), JwtUtil.ACCESS_TOKEN_TIME);
        String refreshToken = jwtUtil.createToken(user.getEmail(), JwtUtil.REFRESH_TOKEN_TIME);
        saveRefreshTokenToRedis(user.getEmail(), refreshToken, browser);
        return TokenResponseDto.of(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public void signOut(String email) {
        redisDAO.deleteValues(email);
    }

    @Override
    @Transactional
    public TokenResponseDto reissue(String refreshToken, String browser) throws JsonProcessingException {
        String email = jwtUtil.getLoginIdFromToken(refreshToken);
        if(!isSameRefreshTokenInRedis(email, browser, refreshToken)){
            throw new IllegalArgumentException("토큰 불일치");
        }
        String newAccessToken = jwtUtil.createToken(email, JwtUtil.ACCESS_TOKEN_TIME);
        String newRefreshToken = jwtUtil.createToken(email, JwtUtil.REFRESH_TOKEN_TIME);
        saveRefreshTokenToRedis(email, newRefreshToken, browser);
        return TokenResponseDto.of(newAccessToken, newRefreshToken);
    }

    private void saveRefreshTokenToRedis(String email, String refreshToken, String browser)
        throws JsonProcessingException {
//        redisDAO.setValues(email, refreshToken.substring(7),
//            Duration.ofMillis(JwtUtil.REFRESH_TOKEN_TIME));
        redisAuthDAO.storeRefreshToken(email, browser, refreshToken,Duration.ofMillis(JwtUtil.REFRESH_TOKEN_TIME)) ;
    }

    private boolean isSameRefreshTokenInRedis(String email, String browser, String refreshToken)
        throws JsonProcessingException {
        redisAuthDAO.isRefreshTokenExpired(email, browser, System.currentTimeMillis());
        List<Object> refreshTokenData = redisAuthDAO.getRefreshTokenData(email, browser);
        String o = (String) refreshTokenData.get(1);
        String substring = o.substring(7);
        return refreshToken.equals(substring);
    }
}
