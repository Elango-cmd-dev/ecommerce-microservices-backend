package com.elan.email_management_service.DTOs;

import lombok.Data;

@Data
public class OrderEmailRequest {

    private Long orderId;
    private String userEmail;
    private Double amount;
}

