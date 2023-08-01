package com.example.shoppingmallproject.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * DAO (Data Access Object) 즉, Redis 안에 있는 데이터에 접근하기 위해서 사용합니다.
 */
@Repository
public class RedisDAO {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisDAO(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void simpleSetValue(String key, String value, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, value, duration);
    }

    public String simpleGetValue(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void setValues(String key, Object data, Duration duration) throws JsonProcessingException {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String serializedData = objectMapper.writeValueAsString(data); // 객체를 JSON 문자열로 직렬화
        values.set(key, serializedData, duration);
    }

    public <T> T getValues(String key, Class<T> valueType) throws JsonProcessingException {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String serializedData = values.get(key);
        if (serializedData != null) {
            return objectMapper.readValue(serializedData, valueType); // JSON 문자열을 객체로 역직렬화
        }
        throw new NoSuchElementException("키와 대응하는 값이 없습니다");
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    /**
     *
     * @param key 해당하는 키에
     * @param data 데이터를 추가합니다.
     * @param duration 만료시간
     * @param <T> 모든 타입의 객체가 들어올 수 있습니다.
     * @throws JsonProcessingException // Json 직렬화 익셉션이며, objectMapper 사용 시 매서드레벨에서 선언해야 합니다.
     */
    public <T> void addValueToList(String key, T data, Duration duration) throws JsonProcessingException {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        String serializedData = objectMapper.writeValueAsString(data);

        listOperations.rightPush(key, serializedData);
        redisTemplate.expire(key, duration);
    }

    /**
     *
     * @param key 해당하는 키에
     * @param elementType 해당하는 타입을 받아옵니다 ex ) Users.class
     * @param idx 리스트에서 접근할 인덱스 입니다. PK가 아닌 "인덱스" 라는 점이 중요합니다
     * @return 레디스에 어떤 객체가 들어왔있던, <T> 를 통해서 리턴 가능합니다.
     * @param <T>
     * @throws JsonProcessingException
     */
    public <T> T getValueFromList(String key, Class<T> elementType, Long idx) throws JsonProcessingException {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        String serializedData = listOperations.index(key, idx);

        if (serializedData != null) {
            return objectMapper.readValue(serializedData, elementType);
        }

        throw new NoSuchElementException("키와 대응하는 값이 없습니다");
    }

    /**
     *
     * @param key
     * @param data
     * @param duration
     * @param <T>
     * @throws JsonProcessingException
     * List 형태의 밸류를 추가합니다. 리스트 타입의 엔티티를 추가해줍니다!
     */
    public <T> void addListValues(String key, List<T> data, Duration duration) throws JsonProcessingException {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        List<String> serializedList = new ArrayList<>(data.size());

        for (T datum : data) {
            String serializedDatum = objectMapper.writeValueAsString(datum);
            serializedList.add(serializedDatum);
        }

        listOperations.rightPushAll(key, serializedList.toArray(new String[0]));
        redisTemplate.expire(key, duration);
    }

    /**
     *
     * @param key
     * @param elementType
     * @return
     * @param <T>
     * @throws JsonProcessingException
     * 패러미터의 key 에 해당하는 리스트 형태의 밸류들을 모두 가져옵니다. 가져온 데이터는 List<> 형태로 역직렬화 됩니다.
     */
    public <T> List<T> getListValues(String key, Class<T> elementType) throws JsonProcessingException {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        List<String> serializedData = listOperations.range(key, 0, -1);

        if (serializedData != null && !serializedData.isEmpty()) {
            List<T> deserializedData = new ArrayList<>();

            for (String serializedDatum : serializedData) {
                T deserializedDatum = objectMapper.readValue(serializedDatum, elementType);
                deserializedData.add(deserializedDatum);
            }

            return deserializedData;
        }

        throw new NoSuchElementException("키와 대응하는 값이 없습니다");
    }

    /**
     * Redis 에 존재하는 모든 키를 날립니다.
     */
    public void flushAll() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().serverCommands().flushAll();
    }
}

