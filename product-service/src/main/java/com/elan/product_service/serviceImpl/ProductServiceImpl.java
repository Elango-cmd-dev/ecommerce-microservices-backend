package com.elan.product_service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.elan.product_service.DTO.ProductDTO;
import com.elan.product_service.entity.ProductEntity;
import com.elan.product_service.repository.ProductRepository;
import com.elan.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import java.time.Duration;
import org.springframework.http.MediaType;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	private ProductDTO mapToDTO(ProductEntity product) {
		return ProductDTO.builder().productId(product.getProductId()).productName(product.getProductName())
				.productPrice(product.getProductPrice()).productDescription(product.getProductDescription()).build();
	}

	private ProductEntity mapToEntity(ProductDTO product) {
		return ProductEntity.builder().productId(product.getProductId()).productName(product.getProductName())
				.productPrice(product.getProductPrice()).productDescription(product.getProductDescription()).build();
	}

	@Override
    public ResponseEntity<Object> createProduct(ProductDTO productDTO) {
        try {
            ProductEntity product = mapToEntity(productDTO);

            ProductEntity savedProduct = productRepo.save(product);

            ProductDTO responseDTO = mapToDTO(savedProduct);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong while creating the product");
        }
    }
	
	@Override
	public ResponseEntity<Object> getAllProducts() {
		try {
			List<ProductEntity> products = productRepo.findAll();

			if (products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products available");
			}

			List<ProductDTO> dtoList = products.stream().map(this::mapToDTO).collect(Collectors.toList());

			return ResponseEntity.status(HttpStatus.OK).body(dtoList);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong while fetch the product data");
		}
	}

	@Override
	public ResponseEntity<Object> getProductById(Long productId) {
		try {
			ProductEntity product = productRepo.findById(productId).orElse(null);

			if (product == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			}

			ProductDTO dto = mapToDTO(product);
			return ResponseEntity.status(HttpStatus.OK).body(dto);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong while fetch the product data");
		}
	}

	@Override
	public ResponseEntity<Object> updateProduct(Long productId, ProductDTO dto) {
		try {
			ProductEntity existingProduct = productRepo.findById(productId).orElse(null);

			if (existingProduct == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			}

			existingProduct.setProductName(dto.getProductName());
			existingProduct.setProductPrice(dto.getProductPrice());
			existingProduct.setProductDescription(dto.getProductDescription());

			ProductEntity updatedProduct = productRepo.save(existingProduct);

			ProductDTO responseDTO = mapToDTO(updatedProduct);

			return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong while updating the product");
		}
	}

	@Override
	public ResponseEntity<Object> deleteProductById(Long productId) {
		try {
			ProductEntity existingProduct = productRepo.findById(productId).orElse(null);

			if (existingProduct == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			}

			productRepo.delete(existingProduct);

			return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong while deleting the product");
		}
	}
	
	// inside ProductServiceImpl class (add methods near other service methods)

	@Override
	public ResponseEntity<Object> uploadProductImage(Long productId, MultipartFile file) {
	    try {
	        ProductEntity product = productRepo.findById(productId).orElse(null);
	        if (product == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	        }

	        product.setImage(file.getBytes());
	        product.setImageContentType(file.getContentType());

	        productRepo.save(product);

	        return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to upload image: " + ex.getMessage());
	    }
	}

	@Override
	public ResponseEntity<byte[]> fetchProductImage(Long productId) {
	    try {
	        ProductEntity product = productRepo.findById(productId).orElse(null);
	        if (product == null || product.getImage() == null) {
	            return ResponseEntity.notFound().build();
	        }

	        byte[] data = product.getImage();
	        String contentType = product.getImageContentType();
	        if (contentType == null) {
	            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	        }

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType(contentType));
	        // Allow browser caching for 1 day - adjust as needed
	        headers.setCacheControl(CacheControl.maxAge(Duration.ofDays(1)).cachePublic());

	        return new ResponseEntity<>(data, headers, HttpStatus.OK);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


}
