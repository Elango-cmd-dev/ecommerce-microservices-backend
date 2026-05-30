package com.elan.user_management_service.util;

import lombok.Data;

@Data
public class WelcomeEmailRequest {
    private String email;
    private String userName;
}

