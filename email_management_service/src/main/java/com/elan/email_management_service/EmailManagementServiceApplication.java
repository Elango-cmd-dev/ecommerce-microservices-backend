package com.elan.email_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailManagementServiceApplication.class, args);
	}

}
