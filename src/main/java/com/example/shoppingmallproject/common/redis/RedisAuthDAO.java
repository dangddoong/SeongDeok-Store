package com.example.shoppingmallproject.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RedisAuthDAO {
    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final ObjectMapper objectMapper;

    public RedisAuthDAO(RedisTemplate<String, String> redisTemplate, HashOperations<String, String, String> hashOperations, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
        this.objectMapper = objectMapper;
        redisTemplate.setHashKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        redisTemplate.setHashValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
    }


    /**
     *
     * @param userEmail 레디스 키
     * @param browser 해쉬 키
     * @param refreshToken 해쉬 키의 대응되는 밸류
     * @param expiration 만료 시간 (Duration.ofMills 로 스태틱하게 사용합니다)
     */
    public void storeRefreshToken(String userEmail, String browser, String refreshToken, Duration expiration) {
        List<Object> refreshTokenData = new ArrayList<>(3);
        refreshTokenData.add(browser);
        refreshTokenData.add(refreshToken);
        refreshTokenData.add(expiration.toMillis());
        try {
            String refreshTokenDataString = objectMapper.writeValueAsString(refreshTokenData);
            // List<Object> 형태를 String으로 형변환 (Serializer)
//            Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().serverCommands().flushAll();
//            redisTemplate.opsForHash().put(userEmail, browser, refreshTokenDataString);
            hashOperations.put(userEmail, browser, refreshTokenDataString);
            // userEmail 은 Redis 의 키, browser 는 해쉬키(키 안의 키), 위에서 직렬화한 데이터 = 밸류
            // 따라서 레디스에서 -> HashOperations.get(userEmail, HashKey(==browser)) 하면 저 안의 밸류 가져올 수 있음.
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param userEmail 키로 쓰일 유저이메일
     * @param browser 해쉬키로 쓰일 브라우저 이름(크롬, 사파리, 파이어폭스 ..)
     * @param currentTime 체크하는 시점의 시간. 아규먼트로 "호출" 하실때는 System.currentTimeMillis() 로 호출하시면 됩니다.
     * @return 리프레쉬토큰이 null 이 아니며, currentTime 이 만료 시간인 Duration 보다 크다면 (시간이 지났다면) true 반환합니다.
     */
    public boolean isRefreshTokenExpired(String userEmail, String browser, long currentTime) {
        List<Object> refreshTokenData = getRefreshTokenData(userEmail, browser);
        return refreshTokenData != null && currentTime >= Long.valueOf((Integer) refreshTokenData.get(2));
    }

    /**
     *
     * @param userEmail 키
     * @param browser 해쉬 키
     * @return 유저의 키 안에 있는 해쉬 키의 데이터를 반환합니다. 해쉬 키의 상응 데이터는 List<> 타입입니다.
     */
    public List<Object> getRefreshTokenData(String userEmail, String browser) {
        String refreshTokenDataString = hashOperations.get(userEmail, browser);
        if (refreshTokenDataString != null) {
            try {
                return objectMapper.readValue(refreshTokenDataString, List.class);
                // objectMapper 로
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 
     * @param userEmail 레디스 키
     * @param browser 해쉬 키
     *                해쉬 키를 삭제합니다.
     */
    public void removeRefreshToken(String userEmail, String browser) {
        hashOperations.delete(userEmail, browser);
    }
}
