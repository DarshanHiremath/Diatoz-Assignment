package com.diatoz.sms.dto;

import com.diatoz.sms.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponse {
	private String userId;
	private String userFirstName;
	private String userLastName;
	private String userName;
	private String userEmail;
	private UserRole userRole;
}
