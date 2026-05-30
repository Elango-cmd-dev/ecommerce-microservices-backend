package com.elan.user_management_service.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManagementRepository extends JpaRepository<UserManagementEntity, Long>{

	UserManagementEntity findByUserEmailId(String userEmailId);

	boolean existsByUserEmailId(String userEmailId);
	
	boolean existsByUserPhoneNumber(String userPhoneNumber);
}
