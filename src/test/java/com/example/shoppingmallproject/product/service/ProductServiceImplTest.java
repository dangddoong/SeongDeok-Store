package com.example.shoppingmallproject.product.service;

import com.example.shoppingmallproject.product.dto.ProductRequestDto;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.repository.ProductRepository;
import com.example.shoppingmallproject.seller.entity.Seller;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_ValidId_ReturnsProduct() {
        // Arrange
        Long productId = 1L;
        Product expectedProduct = mock(Product.class);

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.getProductById(productId);

        // Assert
        assertNotNull(actualProduct);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void getProductById_InvalidId_ThrowsNoSuchElementException() {
        Long invalidProductId = 999L;
        when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getProductById(invalidProductId));
    }

    @Test
    void getProductsByCartIds_ValidCartIds_ReturnsListOfProducts() {
        List<Long> cartIds = Arrays.asList(1L, 2L, 3L);
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product(), new Product());
        when(productRepository.getProductsByCarts(cartIds)).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getUsersProductsByCartIds(cartIds);

        assertNotNull(actualProducts);
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void createProductWithDuplication() {
        Seller seller = mock(Seller.class);

        when(productRepository.existsBySellerAndName(eq(seller.getName()), anyString())).thenReturn(true);

        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("이름");

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(seller, dto));
    }


    @Test
    void 멀티스레드_동시성_테스트() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch countDownLatch = new CountDownLatch(20);
        var b = new Object() {
            int a = 1;
        };
        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(b.a);
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("--------------------------");
                    b.a = b.a + 1;
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println(b.a);
        System.out.println(Thread.currentThread().getName());
    }
}
