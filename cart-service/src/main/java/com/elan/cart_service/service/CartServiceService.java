package com.elan.cart_service.service;

import com.elan.cart_service.dto.CartItemRequestDTO;
import com.elan.cart_service.dto.CartItemResponseDTO;
import com.elan.cart_service.dto.CartResponseDTO;
import reactor.core.publisher.Mono;

public interface CartServiceService {

    Mono<CartItemResponseDTO> addToCart(CartItemRequestDTO request);

    CartResponseDTO getCartByUser(Long userId);

    void removeItem(Long cartItemId);

    void clearCart(Long userId);
    
}
