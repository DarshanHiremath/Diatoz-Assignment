package com.diatoz.sms.dto;

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
public class TestRequest {

	@NotBlank(message = "title is required")
	@NotNull(message = "title is required")
	private String title;

	@NotBlank(message = "subject is required")
	@NotNull(message = "subject is required")
	private String subject;

	@NotBlank(message = "totalMarks is required")
	@NotNull(message = "totalMarks is required")
	private int totalMarks;
}
