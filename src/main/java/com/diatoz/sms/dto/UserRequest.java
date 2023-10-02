package com.diatoz.sms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserRequest {

	@NotNull(message = "Invalid Input")
	@NotBlank(message = "Invalid Input")
	private String userFirstName;

	@NotNull(message = "Invalid Input")
	@NotBlank(message = "Invalid Input")
	private String userLastName;

	@NotNull(message = "Invalid Input")
	@NotBlank(message = "Invalid Input")
	private String userName;

	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String userEmail;
}
