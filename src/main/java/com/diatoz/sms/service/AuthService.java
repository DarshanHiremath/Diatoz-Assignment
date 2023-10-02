package com.diatoz.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.diatoz.sms.dto.AuthRequest;
import com.diatoz.sms.dto.AuthResponse;
import com.diatoz.sms.dto.UserRequest;
import com.diatoz.sms.dto.UserResponse;
import com.diatoz.sms.entity.User;
import com.diatoz.sms.enums.UserRole;
import com.diatoz.sms.repository.UserRepo;
import com.diatoz.sms.security.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthResponse authResponse;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepo userRepo;

	

	public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest, String userRole) {
		User user = new User();
		user.setUserName(userRequest.getUserName());
		user.setUserFirstName(userRequest.getUserFirstName());
		user.setUserLastName(userRequest.getUserLastName());
		user.setUserEmail(userRequest.getUserEmail());

		UserRole role = UserRole.valueOf(userRole.toUpperCase());
		user.setUserRole(role);
		user = userRepo.save(user);

		UserResponse response = new UserResponse();
		response.setUserId(user.getUserId());
		response.setUserFirstName(user.getUserFirstName());
		response.setUserLastName(user.getUserLastName());
		response.setUserName(user.getUserName());
		response.setUserEmail(user.getUserEmail());
		response.setUserRole(user.getUserRole());

		return new ResponseEntity<UserResponse>(response, HttpStatus.CREATED);

	}

	public ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest) {
		log.info("Recieved a request to authenticate");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateJwtToken(authRequest.getUsername());
			authResponse.setToken(token);
			return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
		} else {
			log.error("Failed to authenticate the user : username is not found!");
			throw new UsernameNotFoundException("Failed to authenticate the user.");
		}
	}

	public ResponseEntity<UserResponse> delete(AuthRequest authRequest, String userName) {
		User user = new User();
		if (user.getUserName().equals(authRequest.getUsername())) {
			userRepo.deleteById(userName);
			return new ResponseEntity<UserResponse>(HttpStatus.FOUND);
		} else {
			return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
		}

	}

}
