package com.elan.order_service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.elan.order_service.DTO.OrderResponseDTO;
import com.elan.order_service.DTO.ProductDTO;
import com.elan.order_service.entity.OrderEntity;
import com.elan.order_service.repository.OrderRepository;
import com.elan.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<OrderResponseDTO> placeOrder(OrderEntity order) {

        // Expect order to already contain: userId, productId, quantity
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/products/{id}", order.getProductId())
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .map(productDTO -> {

                    OrderEntity savedOrder = orderRepository.save(order);

                    OrderResponseDTO dto = new OrderResponseDTO();
                    dto.setOrderId(savedOrder.getOrderId());
                    dto.setUserId(savedOrder.getUserId());
                    dto.setProductId(savedOrder.getProductId());
                    dto.setQuantity(savedOrder.getQuantity());

                    dto.setProductName(productDTO.getProductName());
                    dto.setProductPrice(productDTO.getProductPrice());
                    dto.setTotalPrice(savedOrder.getQuantity() * productDTO.getProductPrice());

                    return dto;
                });
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return mapOrders(orderRepository.findAll());
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return mapOrders(orderRepository.findByUserId(userId));
    }

    private List<OrderResponseDTO> mapOrders(List<OrderEntity> orders) {

        List<OrderResponseDTO> list = new ArrayList<>();

        for (OrderEntity order : orders) {

            ProductDTO product = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/products/{id}", order.getProductId())
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();   // OK for synchronous GET

            if (product == null) continue;

            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getOrderId());
            dto.setUserId(order.getUserId());
            dto.setProductId(order.getProductId());
            dto.setQuantity(order.getQuantity());

            dto.setProductName(product.getProductName());
            dto.setProductPrice(product.getProductPrice());
            dto.setTotalPrice(order.getQuantity() * product.getProductPrice());

            list.add(dto);
        }

        return list;
    }
}
