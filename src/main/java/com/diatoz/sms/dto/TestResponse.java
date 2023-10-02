package com.diatoz.sms.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TestResponse {

	private String testId;
	private String title;
	private String subject;
	private int totalMarks;
	private Date createdDate;
	private String score;
}
