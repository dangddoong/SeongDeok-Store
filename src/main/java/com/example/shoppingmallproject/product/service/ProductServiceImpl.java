package com.example.shoppingmallproject.product.service;

import com.example.shoppingmallproject.order.dto.OrderDetailsDto;
import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.product.dto.ProductRequestDto;
import com.example.shoppingmallproject.product.dto.ProductResponseDto;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.repository.ProductRepository;
import com.example.shoppingmallproject.seller.entity.Seller;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    @Transactional(readOnly = true)
    @Override
    public Product getProductById(Long productId){
        return productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException("해당 상품이 존재하지 않습니다.")
        );
    }
    @Transactional(readOnly = true)
    @Override
    public List<Product> getUsersProductsByCartIds(List<Long> cartIds){
        return productRepository.getProductsByCarts(cartIds);
    }
    @Transactional
    @Override
    public Long createProduct(Seller seller, ProductRequestDto dto){
        Product product = Product.builder()
                .detail(dto.getDetail())
                .name(dto.getName())
                .stock(dto.getStock())
                .price(dto.getPrice())
                .seller(seller)
                .build();

        if (productRepository.existsBySellerAndName(seller.getName(), dto.getName())){ // 판매자
            throw new IllegalArgumentException("이미 등록한 상품입니다.");
        }

        productRepository.save(product);

        return product.getId();
    }

    // TODO: 2023/07/18 해당 부분의 테스트코드는 JPA의 더티체킹 이용하고 있는 이유로, 미뤄둔 상황입니다.
    @Transactional
    @Override
    public void updateProduct(Seller seller, Long productId, ProductRequestDto dto){
        Product product = productRepository.getProductWithSeller(productId);

        if (!Objects.equals(product.getSeller().getId(), seller.getId())){
            throw new IllegalArgumentException("다른 판매자의 상품은 수정할 수 없습니다.");
        }

        product.setProduct(dto);
    }

    @Transactional
    @Override
    public void deleteProduct(Seller seller, Long productId){
        Product product = productRepository.getProductWithSeller(productId);

        if (!Objects.equals(product.getSeller().getId(), seller.getId())){
            throw new IllegalArgumentException("다른 판매자의 상품은 삭제할 수 없습니다.");
        }

        productRepository.delete(product);
    }
    @Transactional(readOnly = true)
    @Override
    public List<ProductResponseDto> getSellersProducts(Seller seller){
        return productRepository.getMyProducts(seller.getId());
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    @Override
    public List<Product> getProductsByIds(OrderRequestDto dto, List<Long> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);
        Map<Long, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getId, Function.identity()));
        reduceProductsStock(dto.getOrderDetailsDtos(), productMap);
        return products;
    }
    @Transactional
    public void reduceProductsStock(List<OrderDetailsDto> orderDetailsDtos,
        Map<Long, Product> productMap){
        for (OrderDetailsDto detailsDto: orderDetailsDtos){
            Product product = productMap.get(detailsDto.getProductId());
//            product.reduceStock(detailsDto.getQuantity());
            if(!productRepository.reduceStockAndCheckAvailability(detailsDto.getQuantity(), product.getId())){
                throw new RuntimeException();
            };
        }
    }
}
