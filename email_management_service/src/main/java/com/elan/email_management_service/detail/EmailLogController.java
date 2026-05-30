package com.elan.email_management_service.detail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elan.email_management_service.DTOs.EmailRequest;
import com.elan.email_management_service.DTOs.OrderEmailRequest;
import com.elan.email_management_service.DTOs.UserEmailRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailLogController {

    private final EmailLogService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest request) {
        emailService.sendEmail(request);
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/order-confirmation")
    public ResponseEntity<String> sendOrderEmail(@RequestBody OrderEmailRequest request) {
        emailService.sendOrderConfirmation(request);
        return ResponseEntity.ok("Order confirmation email sent");
    }

    @PostMapping("/welcome")
    public ResponseEntity<String> sendWelcomeEmail(@RequestBody UserEmailRequest request) {
        emailService.sendWelcomeEmail(request);
        return ResponseEntity.ok("Welcome email sent");
    }
}

