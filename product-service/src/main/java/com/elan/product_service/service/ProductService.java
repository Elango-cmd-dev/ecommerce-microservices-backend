package com.elan.product_service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.elan.product_service.DTO.ProductDTO;

public interface ProductService {
	
	ResponseEntity<Object> createProduct(ProductDTO productDTO);

	ResponseEntity<Object> getAllProducts();

	ResponseEntity<Object> getProductById(Long productId);

	ResponseEntity<Object> updateProduct(Long productId, ProductDTO dto);

	ResponseEntity<Object> deleteProductById(Long productId);
	
    ResponseEntity<Object> uploadProductImage(Long productId, MultipartFile file);
    ResponseEntity<byte[]> fetchProductImage(Long productId);

}
