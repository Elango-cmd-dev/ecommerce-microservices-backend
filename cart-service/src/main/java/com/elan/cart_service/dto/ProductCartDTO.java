package com.elan.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartDTO {
    private Long productId;
    private String productName;
    private double productPrice;
    private String productDescription;
}