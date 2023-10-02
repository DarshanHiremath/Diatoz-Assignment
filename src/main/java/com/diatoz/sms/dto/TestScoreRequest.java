package com.diatoz.sms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class TestScoreRequest {

	@NotBlank(message = "score is required")
	@NotNull(message = "score is required")
	@Min(value = 1, message = "score must be valid")
	@Max(value = 100, message = "score must be valid")
	private int score;
}
