package com.exercise.listingproject.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.listingproject.dto.CreateUserRequestDto;
import com.exercise.listingproject.dto.CreateUserResponseDto;
import com.exercise.listingproject.dto.GetSpecificUserRequestDto;
import com.exercise.listingproject.dto.GetSpecificUserResponseDto;
import com.exercise.listingproject.dto.GetUsersRequestDto;
import com.exercise.listingproject.dto.GetUsersResponseDto;
import com.exercise.listingproject.service.UserServices;

@Validated
@RestController
public class UserController {
	@Autowired
	private UserServices services;

	// Get All Users
	@GetMapping("/users")
	public GetUsersResponseDto getAllUsers(
			@RequestParam(name = "pageNum", defaultValue = "1") @Min(1) Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize) {
		GetUsersRequestDto requestDto = new GetUsersRequestDto();
		requestDto.setPageNum(pageNum - 1);
		requestDto.setPageSize(pageSize);

		return services.getAllUsers(requestDto);
	}

	// Get User By Id
	@GetMapping("/users/{id}")
	public GetSpecificUserResponseDto getSpecificUser(@PathVariable Integer id) {
		GetSpecificUserRequestDto requestDto = new GetSpecificUserRequestDto();
		requestDto.setId(id);

		return services.getSpecificUser(requestDto);
	}

	// Create User
	@PostMapping("/users")
	public CreateUserResponseDto createUser(@Valid CreateUserRequestDto requestDto) {
		return services.createUser(requestDto);
	}
}
