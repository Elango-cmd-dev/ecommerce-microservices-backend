package com.elan.user_management_service.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserManagementDTO {

	private Long userId;
	private String userName;
	private String userEmailId;
	private String userPassword;
	private String userPhoneNumber;
	
}
