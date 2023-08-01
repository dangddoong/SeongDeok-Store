package com.example.shoppingmallproject.cart.service;

import com.example.shoppingmallproject.cart.dto.CartRequestDto;
import com.example.shoppingmallproject.cart.dto.CartsWithProductsDto;
import com.example.shoppingmallproject.cart.entity.Cart;
import com.example.shoppingmallproject.cart.repository.CartRepository;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.service.ProductService;
import com.example.shoppingmallproject.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CartServiceImplTest {
    @InjectMocks
    CartServiceImpl cartService;
    @Mock
    ProductService productService;

    @Mock
    CartRepository cartRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetCartsWithProducts() {
        //given
        Product product1 = Product.builder().name("1").build();
        Product product2 = Product.builder().name("2").build(); // 프로덕트 객체 생성

        Cart cart1 = Cart.builder().product(product1).build();
        Cart cart2 = Cart.builder().product(product2).build(); // 카트 객체 생성

        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);

        List<Long> cartIds = carts.stream().map(Cart::getId).toList();
        // 카트 아이디들만 뽑음 (매서드에서 사용됨)

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        //when
        when(cartRepository.findCartsByUserId(1L)).thenReturn(carts);
        // 1번유저가 카트들을 가질거라고 가정
        when(productService.getUsersProductsByCartIds(cartIds)).thenReturn(products);
        // 카트 아이디들로 검색 후 나오는 프로덕트들이 products 임을 가정

        // 실제 수행부분
        List<CartsWithProductsDto> result = cartService.getCartsWithProducts(1L);

        // then
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0).getProduct());
        assertEquals(product2, result.get(1).getProduct());
        assertEquals("1", result.get(0).getProduct().getName());
        assertEquals("2", result.get(1).getProduct().getName());

        verify(productService, times(1)).getUsersProductsByCartIds(cartIds);
        verify(cartRepository, times(1)).findCartsByUserId(1L);
    }

    @Test
    void createCartWithDuplicatedProduct() {
        // given
        CartRequestDto cartRequestDto = new CartRequestDto();

        Long userId = 1L;

        Long productId = 100L;

        User user = mock(User.class);

        cartRequestDto.setProductId(productId);

        //when
        when(cartRepository.isProductAlreadyExist(userId, productId)).thenReturn(true);
        when(user.getId()).thenReturn(1L);

        //then
        assertThrows(IllegalArgumentException.class, () -> cartService.createCart(cartRequestDto, user));
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void deleteCartWithNoSuchElement(){
        //when
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchElementException.class, () -> cartService.deleteCart(1L, mock(User.class)));
        verify(cartRepository, never()).delete(any(Cart.class));
    }

    @Test
    public void testDeleteCartWithInvalidUser() {
        // given
        User validUser = User.builder()
                .email("email") // getEmail 매서드를 통해서 확인하므로
                .build();
        User invalidUser = User.builder()
                .email("wrong")
                .build();

        Cart mocked = mock(Cart.class);

        // when
        when(mocked.getId()).thenReturn(5L); // 목객체는 아이디 없으므로
        when(mocked.getUser()).thenReturn(validUser); // 목객체가 가질 유저
        when(cartRepository.findById(5L)).thenReturn(Optional.of(mocked));

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            cartService.deleteCart(5L, invalidUser);
        });
        verify(cartRepository, never()).delete(mocked);
    }
}