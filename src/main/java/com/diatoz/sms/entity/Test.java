package com.diatoz.sms.entity;

import java.util.Date;
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
@Document(collection = "test_data")
public class Test {
	
	@Id
	private String testId;
	private String title;
	private String subject;
	private int totalMarks;
	private Date createdDate;
	private String teacherId;
	
}
