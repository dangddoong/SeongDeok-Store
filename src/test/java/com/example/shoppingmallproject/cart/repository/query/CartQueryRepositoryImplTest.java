package com.example.shoppingmallproject.cart.repository.query;

import com.example.shoppingmallproject.cart.entity.Cart;
import com.example.shoppingmallproject.cart.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartQueryRepositoryImplTest {

    @Autowired
    private CartRepository cartRepository;

    /**
     * 해당 테스트로 우리는 SELECT * FROM TABLE WHERE USERID = ?; 쿼리의 결과를 얻습니다.
     * 현재 데이터가 없어서 상관 없지만, 이 테스트를 통해서 여러 추가적인 테스트와, 비즈니스 로직의 발전 여지를 볼 수 있습니다.
     * 예시1 ) SELECT *(==asterisk) FROM ~ 과 같은 쿼리는 모든 필드를 조회하는 것이기 때문에, JPA Projection 기능을 사용해볼 수 있을 듯 합니다.
     * 예시2 ) where 조건절이 null 이 들어올 경우엔 어떻게 될까요?
     */

    @Test
    void whenListsAreNull(){
        //assertDoesNotThrow 및 Throws 는 1st Argument 로 "Executable" 을 넣어줘야 합니다. 따라서 람다식으로 작성했습니다.
        assertDoesNotThrow(() -> {
            List<Cart> cartsByUserId = cartRepository.findCartsByUserId(1L);
            assertEquals(0, cartsByUserId.size());
        });

        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            cartRepository.findCartsByUserId(null);
        });
    }
}