package com.diatoz.sms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diatoz.sms.dto.PasswordRequest;
import com.diatoz.sms.dto.UserRequest;
import com.diatoz.sms.dto.UserResponse;
import com.diatoz.sms.entity.User;
import com.diatoz.sms.exception.UserNotFoundByIdException;
import com.diatoz.sms.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<UserResponse> updateUser(UserRequest userRequest, String userId) {
		Optional<User> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();
			user.setUserEmail(userRequest.getUserEmail());
			user.setUserFirstName(userRequest.getUserFirstName());
			user.setUserLastName(userRequest.getUserLastName());
			user.setUserName(userRequest.getUserName());
			user = userRepo.save(user);

			UserResponse response = toUserResponse(user);
			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		} else
			throw new UserNotFoundByIdException("Failed to update the user.");
	}

	public ResponseEntity<UserResponse> deleteUser(String userId) {
		Optional<User> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();
			userRepo.delete(user);
			UserResponse response = toUserResponse(user);
			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		} else
			throw new UserNotFoundByIdException("Failed to delete the user.");
	}

	public ResponseEntity<List<UserResponse>> getUsersByUserRole(String userRole) {
		List<User> users = userRepo.getUsersByUserrole(userRole);
		List<UserResponse> userResponses = new ArrayList<>();
		for (User user : users) {
			UserResponse response = toUserResponse(user);
			userResponses.add(response);
		}

		return new ResponseEntity<List<UserResponse>>(userResponses, HttpStatus.FOUND);
	}

	public ResponseEntity<UserResponse> getUserByUserId(String userId) {

		Optional<User> optional = userRepo.findById(userId);

		if (optional.isPresent()) {

			User user = optional.get();
			UserResponse response = toUserResponse(user);
			return new ResponseEntity<UserResponse>(response, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<UserResponse> addPassword(PasswordRequest passwordRequest, String userId) {
		Optional<User> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();
			user.setUserPassword(passwordEncoder.encode(passwordRequest.getPassword()));
			user = userRepo.save(user);

			UserResponse userResponse = toUserResponse(user);
			return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
		} else {
			throw new UserNotFoundByIdException("Failed to add password to user");
		}

	}

	private UserResponse toUserResponse(User user) {
		UserResponse response = new UserResponse();
		response.setUserId(user.getUserId());
		response.setUserFirstName(user.getUserFirstName());
		response.setUserLastName(user.getUserLastName());
		response.setUserName(user.getUserName());
		response.setUserEmail(user.getUserEmail());
		response.setUserRole(user.getUserRole());
		return response;
	}

}
