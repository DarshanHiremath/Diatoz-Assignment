package com.diatoz.sms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diatoz.sms.dto.TestScoreRequest;
import com.diatoz.sms.dto.TestScoreResponse;
import com.diatoz.sms.entity.TestScore;
import com.diatoz.sms.exception.TestNotFoundException;
import com.diatoz.sms.repository.TestScoreRepo;

@Service
public class TestScoreService {


	@Autowired
	TestScoreRepo testScoreRepo;

	public ResponseEntity<TestScore> createTestScore(TestScoreRequest scoreRequest, String testId, String studentId) {

		System.err.println(scoreRequest.getScore());

		TestScore score = new TestScore();
		score.setStudentId(studentId);
		score.setTestId(testId);
		score.setScore(scoreRequest.getScore());

		score = testScoreRepo.save(score);

		return new ResponseEntity<TestScore>(score, HttpStatus.CREATED);
	}

	public ResponseEntity<List<TestScoreResponse>> getAlltestsScore() {
		List<TestScore> scores = testScoreRepo.findAll();
		List<TestScoreResponse> testScoreResponses = new ArrayList<>();
		for (TestScore score : scores) {
			TestScoreResponse response = new TestScoreResponse();
			response.setScore(score.getScore());
			response.setScoreId(score.getScoreId());
			response.setStudentId(score.getStudentId());
			testScoreResponses.add(response);
		}

		return new ResponseEntity<List<TestScoreResponse>>(testScoreResponses, HttpStatus.OK);
	}

	public ResponseEntity<TestScoreResponse> updateTestScoreData(TestScoreRequest testResquest, String testId) {

		Optional<TestScore> optional = testScoreRepo.findById(testId);
		if (optional.isPresent()) {
			TestScore testScore = optional.get();
			testScore.setScore(testResquest.getScore());
			testScore = testScoreRepo.save(testScore);

			TestScoreResponse scoreResponse = new TestScoreResponse();
			scoreResponse.setScore(testScore.getScore());
			scoreResponse.setStudentId(testScore.getStudentId());
			scoreResponse.setScoreId(testScore.getScoreId());

			return new ResponseEntity<TestScoreResponse>(scoreResponse, HttpStatus.OK);

		} else {
			throw new TestNotFoundException("Failed to update the score");
		}

	}

	public ResponseEntity<TestScoreResponse> deleteTestScoreData(String testId) {

		Optional<TestScore> optional = testScoreRepo.findById(testId);
		if (optional.isPresent()) {
			TestScore score = optional.get();
			testScoreRepo.deleteById(testId);

			TestScoreResponse response = new TestScoreResponse();
			response.setScore(score.getScore());
			response.setScoreId(score.getScoreId());
			response.setStudentId(score.getStudentId());

			return new ResponseEntity<TestScoreResponse>(response, HttpStatus.OK);
		} else
			throw new TestNotFoundException("Failed to delete the test score");
	}

	public ResponseEntity<TestScoreResponse> getTestScoreById(String testScoreId) {

		Optional<TestScore> optional = testScoreRepo.findById(testScoreId);
		if (optional.isPresent()) {
			TestScore testScore = optional.get();
			TestScoreResponse response = new TestScoreResponse();
			response.setScore(testScore.getScore());
			response.setScoreId(testScore.getScoreId());
			response.setStudentId(testScore.getStudentId());
			return new ResponseEntity<TestScoreResponse>(response, HttpStatus.OK);
		} else
			throw new TestNotFoundException("Failed to fetch the test score");
	}
}
