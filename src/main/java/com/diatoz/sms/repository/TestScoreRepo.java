package com.diatoz.sms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diatoz.sms.entity.TestScore;

public interface TestScoreRepo extends MongoRepository<TestScore, String> {

	public List<TestScore> findByStudentId(String userId);

	

}
