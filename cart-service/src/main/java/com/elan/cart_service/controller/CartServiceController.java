package com.elan.cart_service.controller;


import org.springframework.web.bind.annotation.*;
import com.elan.cart_service.dto.CartItemRequestDTO;
import com.elan.cart_service.dto.CartItemResponseDTO;
import com.elan.cart_service.dto.CartResponseDTO;
import com.elan.cart_service.service.CartServiceService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartServiceController {

    private final CartServiceService cartService;

    @PostMapping
    public Mono<CartItemResponseDTO> addToCart(@RequestBody CartItemRequestDTO request) {
        return cartService.addToCart(request);
    }

    @GetMapping("/user/{userId}")
    public CartResponseDTO getCartByUser(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }

    @DeleteMapping("/item/{cartItemId}")
    public void removeItem(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
    }

    @DeleteMapping("/user/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}

