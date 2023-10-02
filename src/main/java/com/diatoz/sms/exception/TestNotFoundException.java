package com.diatoz.sms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class TestNotFoundException extends RuntimeException {
	private String message;
}
