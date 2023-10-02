package com.diatoz.sms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.diatoz.sms.dto.TestRequest;
import com.diatoz.sms.dto.TestResponse;
import com.diatoz.sms.entity.Test;
import com.diatoz.sms.entity.TestScore;
import com.diatoz.sms.entity.User;
import com.diatoz.sms.enums.UserRole;
import com.diatoz.sms.exception.TestNotFoundException;
import com.diatoz.sms.repository.TestRepo;
import com.diatoz.sms.repository.TestScoreRepo;
import com.diatoz.sms.repository.UserRepo;

@Service
public class TestService {

	@Autowired
	private TestRepo testRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TestScoreRepo scoreRepo;

	private User getAutheticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userRepo.findByUserName(username);
		return user;
	}

	public ResponseEntity<Test> createTest(TestRequest testResquest) {

		User user = getAutheticatedUser();

		Test test = new Test();
		test.setSubject(testResquest.getSubject());
		test.setTitle(testResquest.getTitle());
		test.setTotalMarks(testResquest.getTotalMarks());
		test.setCreatedDate(new Date(System.currentTimeMillis()));
		test.setTeacherId(user.getUserId());

		test = testRepo.save(test);

		return new ResponseEntity<Test>(test, HttpStatus.CREATED);
	}

	public ResponseEntity<List<TestResponse>> getAlltests() {

		User user = getAutheticatedUser();
//
//		List<TestResponse> responses = new ArrayList<>();
//		if (user.getUserRole().equals(UserRole.STUDENT)) {
//			List<TestScore> scores = scoreRepo.findByStudentId(user.getUserId());
//			if (scores.size() > 0) {
//				for (TestScore score : scores) {
//					Optional<Test> optional = testRepo.findById(score.getTestId());
//					Test test = optional.get();
//					TestResponse response = getTestResponse(test);
//					response.setScore(String.valueOf(score.getScore()));
//					responses.add(response);
//				}
//			}

		List<TestResponse> responses = new ArrayList<>();

		if (user.getUserRole() == UserRole.STUDENT) {
			List<TestScore> scores = scoreRepo.findByStudentId(user.getUserId());
			System.out.println(user.getUserId());
			for (TestScore score : scores) {
				Optional<Test> optional = testRepo.findById(score.getTestId());
				System.out.println(score.getTestId());
				if (optional.isPresent()) {
					Test test = optional.get();
					TestResponse response = getTestResponse(test);
					response.setScore(String.valueOf(score.getScore()));
					responses.add(response);
				}
			}
		} else if (user.getUserRole().equals(UserRole.TEACHER)) {

			List<Test> tests = testRepo.findByTeacherId(user.getUserId());
			if (tests.size() >= 0) {
				for (Test test : tests) {
					TestResponse response = getTestResponse(test);
					response.setScore("/test-scores/" + test.getTestId()); // /test-scores/kjadsbhfjewahfsadji
					responses.add(response);
				}
			}

		}

		return new ResponseEntity<List<TestResponse>>(responses, HttpStatus.FOUND);
	}

	private TestResponse getTestResponse(Test test) {

		TestResponse response = new TestResponse();
		response.setTestId(test.getTestId());
		response.setTitle(test.getTitle());
		response.setSubject(test.getSubject());
		response.setTotalMarks(test.getTotalMarks());
		response.setCreatedDate(test.getCreatedDate());
		return response;
	}

	public ResponseEntity<Test> updateTestData(TestRequest testResquest, String testId) {

		Optional<Test> optional = testRepo.findById(testId);
		if (optional.isPresent()) {
			Test test = optional.get();
			test.setSubject(testResquest.getSubject());
			test.setTitle(testResquest.getTitle());
			test.setTotalMarks(testResquest.getTotalMarks());
			test = testRepo.save(test);
			return new ResponseEntity<Test>(test, HttpStatus.OK);

		} else {
			throw new TestNotFoundException("Unable to update the test data!");
		}
	}

	public ResponseEntity<Test> deleteTestData(String testId) {

		Optional<Test> optional = testRepo.findById(testId);
		if (optional.isPresent()) {
			Test test = optional.get();
			testRepo.deleteById(testId);
			return new ResponseEntity<Test>(test, HttpStatus.OK);

		} else {
			throw new TestNotFoundException("Unable to delete the test data!");
		}
	}

}
