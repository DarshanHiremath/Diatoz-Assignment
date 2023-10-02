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

import com.diatoz.sms.dto.TestScoreRequest;
import com.diatoz.sms.dto.TestScoreResponse;
import com.diatoz.sms.entity.TestScore;
import com.diatoz.sms.service.TestScoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class TestScoreController {

	@Autowired
	TestScoreService testScoreService;

	@Operation(description = "**Create Test Score -**" + " the API endpoint is used to create test score", responses = {
			@ApiResponse(responseCode = "200", description = "Test Score created successfully", content = {
					@Content(schema = @Schema(implementation = TestScore.class)) }),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasAuthority('TEACHER')")
	@PostMapping("/tests/{testId}/students/{studentId}/test-scores")
	public ResponseEntity<TestScore> createTestScore(@RequestBody TestScoreRequest scoreRequest,
			@PathVariable String testId, @PathVariable String studentId) {
		return testScoreService.createTestScore(scoreRequest, testId, studentId);
	}

	@Operation(description = "**Get all test scores-**"
			+ " the API endpoint is used to fetch all test scores", responses = {
					@ApiResponse(responseCode = "200", description = "Test Scores fetched successfully", content = {
							@Content(schema = @Schema(implementation = TestScoreResponse.class)) }),
					@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasAuthority('TEACHER') OR hasAuthority('STUDENT')")
	@GetMapping("/test-scores")
	public ResponseEntity<List<TestScoreResponse>> getAllTestScores() {
		return testScoreService.getAlltestsScore();
	}

	@Operation(description = "**Get test score by scoreId -**"
			+ " the API endpoint is used to fetch test score by scoreId", responses = {
					@ApiResponse(responseCode = "302", description = "Test Score fetched successfully", content = {
							@Content(schema = @Schema(implementation = TestScore.class)) }),
					@ApiResponse(responseCode = "404", description = "scoreId Not Found") })
	@GetMapping("/test-scores/{testScoreId}")
	public ResponseEntity<TestScoreResponse> getTestScoreById(@PathVariable String testScoreId) {
		return testScoreService.getTestScoreById(testScoreId);
	}

	@Operation(description = "**Update test score by scoreId -**"
			+ " the API endpoint is used to update test score by scoreId", responses = {
					@ApiResponse(responseCode = "302", description = "Test Score updated successfully", content = {
							@Content(schema = @Schema(implementation = TestScoreResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "scoreId Not Found") })
	@PreAuthorize("hasAuthority('TEACHER')")
	@PutMapping("/test-scores/{testScoreId}")
	public ResponseEntity<TestScoreResponse> updateTestData(@RequestBody TestScoreRequest testResquest,
			@PathVariable String testScoreId) {
		return testScoreService.updateTestScoreData(testResquest, testScoreId);
	}

	@Operation(description = "**Delete test score by scoreId -**"
			+ " the API endpoint is used to delete test score by scoreId", responses = {
					@ApiResponse(responseCode = "302", description = "Test Score deleted successfully", content = {
							@Content(schema = @Schema(implementation = TestScoreResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "scoreId Not Found") })
	@PreAuthorize("hasAuthority('TEACHER')")
	@DeleteMapping("/test-scores/{testScoreId}")
	public ResponseEntity<TestScoreResponse> deleteTestData(@PathVariable String testScoreId) {
		return testScoreService.deleteTestScoreData(testScoreId);
	}
}
