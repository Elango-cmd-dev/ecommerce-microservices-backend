package com.elan.email_management_service.detail;

import com.elan.email_management_service.DTOs.EmailRequest;
import com.elan.email_management_service.DTOs.OrderEmailRequest;
import com.elan.email_management_service.DTOs.UserEmailRequest;

public interface EmailLogService {

    void sendEmail(EmailRequest request);

    void sendOrderConfirmation(OrderEmailRequest request);

    void sendWelcomeEmail(UserEmailRequest request);
}

