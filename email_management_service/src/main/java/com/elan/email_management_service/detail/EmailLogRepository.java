package com.elan.email_management_service.detail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLogEntity, Long> {
}

