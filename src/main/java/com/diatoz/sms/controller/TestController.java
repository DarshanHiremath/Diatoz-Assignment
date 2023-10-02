package com.diatoz.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.diatoz.sms.dto.TestRequest;
import com.diatoz.sms.dto.TestResponse;
import com.diatoz.sms.entity.Test;
import com.diatoz.sms.service.TestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@Operation(description = "**Create Test -**" + " the API endpoint is used to create test", responses = {
			@ApiResponse(responseCode = "201", description = "Test created successfully", content = {
					@Content(schema = @Schema(implementation = Test.class)) }),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasAuthority('TEACHER')")
	@PostMapping("/users/{userId}/tests")
	public ResponseEntity<Test> createTest(@RequestBody TestRequest testResquest) {
		return testService.createTest(testResquest);
	}

	@Operation(description = "**Get all tests -**"
		+ " the API endpoint is used to fetch tests", responses = {
			@ApiResponse(responseCode = "200", description = "Tests fetched successfully", content = {
					@Content(schema = @Schema(implementation = TestResponse.class)) }),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasAuthority('TEACHER') OR hasAuthority('STUDENT')")
	@GetMapping("/tests")
	public ResponseEntity<List<TestResponse>> getAllTests() {
		return testService.getAlltests();
	}

	@Operation(description = "**Update test by testId -**"
			+ " the API endpoint is used to update test score by testId", responses = {
					@ApiResponse(responseCode = "302", description = "Test updated successfully", content = {
							@Content(schema = @Schema(implementation = Test.class)) }),
					@ApiResponse(responseCode = "404", description = "testId Not Found") })
	@PreAuthorize("hasAuthority('TEACHER') ")
	@PutMapping("/tests/{testId}")
	public ResponseEntity<Test> updateTestData(@RequestBody TestRequest testResquest, @PathVariable String testId) {
		return testService.updateTestData(testResquest, testId);
	}

	@Operation(description = "**delete test by testId -**"
			+ " the API endpoint is used to delete test by testId", responses = {
					@ApiResponse(responseCode = "302", description = "Test deleted successfully", content = {
							@Content(schema = @Schema(implementation = Test.class)) }),
					@ApiResponse(responseCode = "404", description = "testId Not Found") })
	@PreAuthorize("hasAuthority('TEACHER')")
	@DeleteMapping("/tests/{testId}")
	public ResponseEntity<Test> deleteTestData(@PathVariable String testId) {
		return testService.deleteTestData(testId);
	}

}
