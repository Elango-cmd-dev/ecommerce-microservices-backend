package com.elan.user_management_service.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EMAIL-SERVICE")
public interface EmailClient {

	@PostMapping("/emails/welcome")
	void sendWelcomeEmail(@RequestBody LoginAlertEmailRequest request);

}

