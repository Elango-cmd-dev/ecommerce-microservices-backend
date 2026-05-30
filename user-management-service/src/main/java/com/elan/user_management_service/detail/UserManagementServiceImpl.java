package com.elan.user_management_service.detail;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.elan.user_management_service.util.EmailClient;
import com.elan.user_management_service.util.LoginAlertEmailRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

	private final UserManagementRepository userManagementRepository;

	private UserManagementDTO entityToDTO(UserManagementEntity entity) {
		return UserManagementDTO.builder().userEmailId(entity.getUserEmailId()).userName(entity.getUserName())
				.userPhoneNumber(entity.getUserPhoneNumber()).build();
	}

	private UserManagementEntity dtoToEntity(UserManagementDTO dto) {
		return UserManagementEntity.builder().userEmailId(dto.getUserEmailId()).userName(dto.getUserName())
				.userPhoneNumber(dto.getUserPhoneNumber()).build();
	}

	@Override
	public ResponseEntity<Object> registerUser(UserManagementDTO dto) {
		try {
			if (userManagementRepository.existsByUserEmailId(dto.getUserEmailId())) {
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			            .body("Email Id already exists");
			}
			if (userManagementRepository.existsByUserPhoneNumber(dto.getUserPhoneNumber())) {
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			            .body("Phone Number already exists");
			}
			UserManagementEntity userManagementEntity = dtoToEntity(dto);
			userManagementEntity.setUserPassword(dto.getUserPassword());
			UserManagementEntity SavedData = userManagementRepository.save(userManagementEntity);
			UserManagementDTO userManagementDTO = entityToDTO(SavedData);
			userManagementDTO.setUserId(SavedData.getUserId());
			return ResponseEntity.status(HttpStatus.CREATED).body(userManagementDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong while creating the user detail");
		}
	}

	@Override
	public ResponseEntity<Object> loginUser(LoginDTO dtoLogin) {
		try {
		    UserManagementEntity user = userManagementRepository.findByUserEmailId(dtoLogin.getUserEmailId());

		    if (user == null || !user.getUserPassword().equals(dtoLogin.getUserPassword())) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		                .body("Invalid email or password");
		    }
		    // Convert entity to DTO
	        UserManagementDTO userDto = entityToDTO(user);

	        sendLoginAlertEmail(user);
	        
	        // Make sure userId is set if needed
	        userDto.setUserId(user.getUserId());

	        // Return JSON with user details
	        return ResponseEntity.ok(userDto);

		} catch (Exception e) {
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body("Something went wrong while processing login");
		}
	}
	
	@Autowired
	private EmailClient emailClient;

	@Async
	public void sendLoginAlertEmail(UserManagementEntity user) {

	    LoginAlertEmailRequest request = new LoginAlertEmailRequest();
	    request.setEmail(user.getUserEmailId());
	    request.setUserName(user.getUserName());
	    request.setLoginTime(LocalDateTime.now().toString());

	    try {
	        emailClient.sendWelcomeEmail(request);
	    } catch (Exception ex) {
	        // LOG ONLY – DO NOT BREAK LOGIN
	        
	    }
	}

}
