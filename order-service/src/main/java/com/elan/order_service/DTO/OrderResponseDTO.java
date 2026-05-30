package com.elan.order_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	
	private Long orderId;
	private Long userId;
	private Long productId;
	private int quantity;
	private double totalPrice;
	
	//Product Detail
	private String productName;
	private double productPrice; 

}
