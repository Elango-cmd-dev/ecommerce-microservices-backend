package com.elan.email_management_service.detail;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.elan.email_management_service.DTOs.EmailRequest;
import com.elan.email_management_service.DTOs.OrderEmailRequest;
import com.elan.email_management_service.DTOs.UserEmailRequest;
import com.elan.email_management_service.util.EmailSendException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailLogServiceImpl implements EmailLogService {

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;

    @Override
    public void sendEmail(EmailRequest request) {
        sendAndLog(request.getTo(), request.getSubject(), request.getBody());
    }

    @Override
    public void sendOrderConfirmation(OrderEmailRequest request) {
        String subject = "Order Confirmation - Order #" + request.getOrderId();
        String body = "Your order has been placed successfully.\nTotal Amount: " + request.getAmount();
        sendAndLog(request.getUserEmail(), subject, body);
    }

    @Override
    public void sendWelcomeEmail(UserEmailRequest request) {
        String subject = "Welcome to Our Store";
        String body = "Hello " + request.getUserName() + ",\nThank you for registering.";
        sendAndLog(request.getEmail(), subject, body);
    }

    private void sendAndLog(String to, String subject, String body) {
        EmailLogEntity log = new EmailLogEntity();
        log.setToEmail(to);
        log.setSubject(subject);
        log.setBody(body);
        log.setCreatedAt(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            log.setStatus("SUCCESS");
        } catch (Exception ex) {
            log.setStatus("FAILED");
            log.setFailureReason(ex.getMessage());
            throw new EmailSendException("Email sending failed");
        } finally {
            emailLogRepository.save(log);
        }
    }
}

