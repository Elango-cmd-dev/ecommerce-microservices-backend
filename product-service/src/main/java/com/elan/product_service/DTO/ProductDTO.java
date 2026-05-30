package com.elan.product_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
	
	private Long productId;
	private String productName;
	private String productPrice;
	private String productDescription;

	private Boolean hasImage;

}
