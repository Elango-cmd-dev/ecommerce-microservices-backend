package com.elan.product_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.elan.product_service.DTO.ProductDTO;
import com.elan.product_service.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
    @PostMapping
    public ResponseEntity<Object> createProductDetail(@RequestBody ProductDTO dto){
        return productService.createProduct(dto);
    }
	
    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO dto) {
        return productService.updateProduct(productId, dto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProductById(productId);
    } 
	
    @PostMapping(path = "/{productId}/image", consumes = {org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadImage(@PathVariable Long productId,
                                              @RequestParam("file") MultipartFile file) {
        return productService.uploadProductImage(productId, file);
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long productId) {
        return productService.fetchProductImage(productId);
    }	
}
