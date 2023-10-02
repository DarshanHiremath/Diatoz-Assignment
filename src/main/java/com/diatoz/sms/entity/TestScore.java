package com.diatoz.sms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document(collection = "test_scores")
public class TestScore {
	
	@Id
	private String scoreId;
	private String testId;
	private String studentId;
	private int score;
}
