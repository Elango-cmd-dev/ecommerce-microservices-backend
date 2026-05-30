package com.elan.cart_service.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {

    private Long userId;
    private Integer totalQuantity;
    private double totalAmount;
    private List<CartItemResponseDTO> items;
}

