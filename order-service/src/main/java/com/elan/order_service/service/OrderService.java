package com.elan.order_service.service;

import java.util.List;

import com.elan.order_service.DTO.OrderResponseDTO;
import com.elan.order_service.entity.OrderEntity;
import reactor.core.publisher.Mono;

public interface OrderService {

	Mono<OrderResponseDTO> placeOrder(OrderEntity order);

	List<OrderResponseDTO> getAllOrders();

	List<OrderResponseDTO> getOrdersByUser(Long userId);
}
