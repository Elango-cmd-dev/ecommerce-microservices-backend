package com.elan.user_management_service.util;

import lombok.Data;

@Data
public class LoginAlertEmailRequest {
    private String email;
    private String userName;
    private String loginTime;
}

