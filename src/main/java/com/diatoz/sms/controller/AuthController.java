package com.diatoz.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.diatoz.sms.dto.AuthRequest;
import com.diatoz.sms.dto.AuthResponse;
import com.diatoz.sms.dto.UserRequest;
import com.diatoz.sms.dto.UserResponse;
import com.diatoz.sms.service.AuthService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "Authenitcation", description = "Endpoints for login and authentication of the user")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Operation(description = "**Authenticate User -**"
			+ " The API endpoint is used to authenticate the user", responses = {
					@ApiResponse(responseCode = "200", description = "User Authenticated Successfully", content = {
							@Content(schema = @Schema(implementation = AuthResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "User Not Found") })
	@PostMapping("/users/sign-in")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
		log.info("Authentication Requested!");
		return authService.authenticate(authRequest);
	}

	@Operation(description = "**Register User -**"
			+ " This API endpoint is used to register a user with the specified user role.", responses = {
					@ApiResponse(responseCode = "200", description = "User Registered Successfully", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "403", description = "Forbidden") })
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/user-roles/{userRole}/users/register")
	public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest, @PathVariable String userRole) {
		return authService.register(userRequest, userRole);
	}
}
