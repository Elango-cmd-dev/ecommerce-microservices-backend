package com.elan.user_management_service.detail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserManagementController {

	private final UserManagementService userManagementService;
	
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody UserManagementDTO dto){
		return userManagementService.registerUser(dto);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody LoginDTO dtoLogin){
		return userManagementService.loginUser(dtoLogin);
	}
}
