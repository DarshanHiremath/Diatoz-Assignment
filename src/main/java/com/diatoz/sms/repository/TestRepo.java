package com.diatoz.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.diatoz.sms.entity.Test;

public interface TestRepo extends MongoRepository<Test, String> {

	@Query("{'studentId':?0}")
	public Optional<Test> findByStudentId(String studentId);

	public List<Test> findByTeacherId(String userId);


}
