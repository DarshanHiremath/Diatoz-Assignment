package com.diatoz.sms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.diatoz.sms.entity.User;

public interface UserRepo extends MongoRepository<User, String> {

	@Query("{'userName':?0}")
	public User findByUserName(String userName);

	@Query("{'userRole':?0}")
	public List<User> getUsersByUserrole(String userRole);

	@Query("{'userId':?0")
	public List<User> getUsersByUserId(String userId);
}
