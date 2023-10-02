package com.diatoz.sms.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.diatoz.sms.dto.PasswordRequest;
//import com.diatoz.sms.dto.AuthResponse;
//import com.diatoz.sms.dto.PasswordRequest;
import com.diatoz.sms.dto.UserRequest;
import com.diatoz.sms.dto.UserResponse;
import com.diatoz.sms.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(description = "**Update user by userId -**"
			+ " the API endpoint is used to update the user based on the userId", responses = {
					@ApiResponse(responseCode = "200", description = "user successfully updated", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found")})
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest, @PathVariable String userId) {
		return userService.updateUser(userRequest, userId);
	}
	
	@Operation(description = "**Add password to userId -**"
			+ " the API endpoint is used to add the user password based on the userId", responses = {
					@ApiResponse(responseCode = "201", description = "user password successfully added", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found")})
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/users/{userId}/add-password")
	public ResponseEntity<UserResponse> addPassword(@RequestBody PasswordRequest passwordRequest, @PathVariable String userId){
		return userService.addPassword(passwordRequest, userId);
	}

	@Operation(description = "**Delete user by userId -**"
			+ " the API endpoint is used to delete the user based on the userId", responses = {
					@ApiResponse(responseCode = "200", description = "user successfully deleted", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found")})
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<UserResponse> deleteUser(@PathVariable String userId) {
		return userService.deleteUser(userId);
	}

	@Operation(description = "**Get user by UserRole -**"
			+ " the API endpoint is used to fetch the user based on the userRole", responses = {
					@ApiResponse(responseCode = "302", description = "user successfully fetched", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found")})
	@GetMapping("/user-roles/{userRole}")
	public ResponseEntity<List<UserResponse>> getUsersByUserRole(@PathVariable String userRole) {
		return userService.getUsersByUserRole(userRole.toUpperCase());
	}

	@Operation(description = "**Get user by userId -**"
			+ " the API endpoint is used to fetch the user based on the userId", responses = {
					@ApiResponse(responseCode = "302", description = "user successfully fetched", content = {
							@Content(schema = @Schema(implementation = UserResponse.class)) }),
					@ApiResponse(responseCode = "404", description = "user not found") })
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserResponse> getUserByUserId(@PathVariable String userId) {
		return userService.getUserByUserId(userId);
	}

}
