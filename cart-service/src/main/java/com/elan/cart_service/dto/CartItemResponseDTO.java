package com.elan.cart_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDTO {

    private Long cartItemId;
    private Long userId;
    private Long productId;
    private Integer quantity;

    private String productName;
    private double productPrice;
    private double totalPrice;  // quantity * price
}
