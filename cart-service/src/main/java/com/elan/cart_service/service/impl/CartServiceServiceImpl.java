package com.elan.cart_service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.elan.cart_service.dto.CartItemRequestDTO;
import com.elan.cart_service.dto.CartItemResponseDTO;
import com.elan.cart_service.dto.CartResponseDTO;
import com.elan.cart_service.dto.ProductCartDTO;
import com.elan.cart_service.entity.CartServiceEntity;
import com.elan.cart_service.repository.CartServiceRepository;
import com.elan.cart_service.service.CartServiceService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CartServiceServiceImpl implements CartServiceService {

    private final CartServiceRepository cartItemRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<CartItemResponseDTO> addToCart(CartItemRequestDTO request) {

        // 1. Get product details from product-service
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/products/{id}", request.getProductId())
                .retrieve()
                .bodyToMono(ProductCartDTO.class)
                .map(productDTO -> {

                    // 2. Save cart item
                	CartServiceEntity entity = CartServiceEntity.builder()
                            .userId(request.getUserId())
                            .productId(request.getProductId())
                            .quantity(request.getQuantity())
                            .build();

                    CartServiceEntity saved = cartItemRepository.save(entity);

                    double lineTotal = request.getQuantity() * productDTO.getProductPrice();

                    // 3. Build response
                    return CartItemResponseDTO.builder()
                            .cartItemId(saved.getCartItemId())
                            .userId(saved.getUserId())
                            .productId(saved.getProductId())
                            .quantity(saved.getQuantity())
                            .productName(productDTO.getProductName())
                            .productPrice(productDTO.getProductPrice())
                            .totalPrice(lineTotal)
                            .build();
                });
    }

    @Override
    public CartResponseDTO getCartByUser(Long userId) {

        List<CartServiceEntity> items = cartItemRepository.findByUserId(userId);
        List<CartItemResponseDTO> itemDTOs = new ArrayList<>();

        int totalQuantity = 0;
        double totalAmount = 0.0;

        for (CartServiceEntity item : items) {

            ProductCartDTO product = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/products/{id}", item.getProductId())
                    .retrieve()
                    .bodyToMono(ProductCartDTO.class)
                    .block();

            if (product == null) {
                continue;
            }

            double lineTotal = item.getQuantity() * product.getProductPrice();

            CartItemResponseDTO dto = CartItemResponseDTO.builder()
                    .cartItemId(item.getCartItemId())
                    .userId(item.getUserId())
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .totalPrice(lineTotal)
                    .build();

            itemDTOs.add(dto);

            totalQuantity += item.getQuantity();
            totalAmount += lineTotal;
        }

        return CartResponseDTO.builder()
                .userId(userId)
                .totalQuantity(totalQuantity)
                .totalAmount(totalAmount)
                .items(itemDTOs)
                .build();
    }

    @Override
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}

