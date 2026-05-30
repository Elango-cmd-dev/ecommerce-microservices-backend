package com.elan.api_gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// PRODUCT SERVICE ROUTE
				.route("product-service", r -> r.path("/products/**").uri("lb://PRODUCT-SERVICE"))

				// ORDER SERVICE ROUTE
				.route("order-service", r -> r.path("/orders/**").uri("lb://ORDER-SERVICE"))

				// USER SERVICE ROUTE
				.route("user-management-service", r -> r.path("/users/**").uri("lb://USER-MANAGEMENT-SERVICE"))
				
				// CART SERVICE ROUTE
				.route("cart-service", r -> r.path("/cart/**").uri("lb://CART-SERVICE"))
				
				// EMAIL SERVICE ROUTE
				.route("email-management-service", r -> r.path("/emails/**").uri("lb://EMAIL-SERVICE"))
				
				.build();
	}
}
