package com.elan.user_management_service.detail;

import org.springframework.http.ResponseEntity;

public interface UserManagementService {

	ResponseEntity<Object> registerUser(UserManagementDTO dto);

	ResponseEntity<Object> loginUser(LoginDTO dtoLogin);

}
