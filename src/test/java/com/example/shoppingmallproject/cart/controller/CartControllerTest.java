package com.example.shoppingmallproject.cart.controller;

import com.example.shoppingmallproject.cart.dto.CartRequestDto;
import com.example.shoppingmallproject.cart.dto.CartsWithProductsDto;
import com.example.shoppingmallproject.cart.service.CartService;
import com.example.shoppingmallproject.common.security.jwt.JwtUtil;
import com.example.shoppingmallproject.common.security.userDetails.entity.UserDetailsImpl;
import com.example.shoppingmallproject.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    private CartService cartService;

    private UserDetailsImpl userDetails;
    private User user;
    @BeforeEach
    void setUp(){
        user = mock(User.class); // user 목 객체 생성
        when(user.getId()).thenReturn(1L); // 유저의 아이디는 1로
        userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUser()).thenReturn(user);  // userDetailsImpl 에서 getUSER() 호출 시 위 유저 반환하도록 설정
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, null));
    }
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getCartsWithProducts_OK() throws Exception {
        Long userId = 1L;
        List<CartsWithProductsDto> expectedCartsWithProducts = new ArrayList<>();
        CartsWithProductsDto cartsWithProductsDto = mock(CartsWithProductsDto.class);
        expectedCartsWithProducts.add(cartsWithProductsDto);
        when(cartService.getCartsWithProducts(userId)).thenReturn(expectedCartsWithProducts);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(cartService, times(1)).getCartsWithProducts(userId);
    }

    @Test
    void getCartsWithProducts_NoContent() throws Exception {
        Long userId = 1L;
        List<CartsWithProductsDto> expectedCartsWithProducts = new ArrayList<>();
        when(cartService.getCartsWithProducts(userId)).thenReturn(expectedCartsWithProducts);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(cartService, times(1)).getCartsWithProducts(userId);
    }

    @Test
    void createCart_OK() throws Exception {
        // Given
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(1001L);
        cartRequestDto.setQuantity(2L);

        when(cartService.createCart(any(CartRequestDto.class), any(User.class))).thenReturn(1L);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cartRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(cartService, times(1)).createCart(any(CartRequestDto.class), any(User.class));
    }

    @Test
    void createCart_BadRequest() throws Exception {
        // Given
        CartRequestDto cartRequestDto = new CartRequestDto();

        when(cartService.createCart(any(CartRequestDto.class), any(User.class)))
                .thenThrow(new IllegalArgumentException("상품이 이미 장바구니에 있습니다."));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cartRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("상품이 이미 장바구니에 있습니다."));

        verify(cartService, times(1)).createCart(any(CartRequestDto.class), any(User.class));
    }

    @Test
    void createCart_NotFound() throws Exception {
        // Given
        CartRequestDto cartRequestDto = new CartRequestDto();

        when(cartService.createCart(any(CartRequestDto.class), any(User.class)))
                .thenThrow(new NoSuchElementException("찾으시는 상품이 존재하지 않습니다."));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cartRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("찾으시는 상품이 존재하지 않습니다."));

        verify(cartService, times(1)).createCart(any(CartRequestDto.class), any(User.class));
    }

    @Test
    void deleteCart_NotFound() throws Exception {
        // Given
        Long cartId = 1L;

        doThrow(new NoSuchElementException("해당 항목이 존재하지 않습니다.")).when(cartService).deleteCart(eq(cartId), any(User.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("해당 항목이 존재하지 않습니다."));

        verify(cartService, times(1)).deleteCart(eq(cartId), any(User.class));
    }

}