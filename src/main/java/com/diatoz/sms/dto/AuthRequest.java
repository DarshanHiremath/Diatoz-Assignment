package com.diatoz.sms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

	@NotBlank(message = "area cannot be blank")
	@NotNull(message = "area cannot be null")
	private String username;
	private String password;
}
