package com.exercise.listingproject.service;

import com.exercise.listingproject.dto.CreateUserRequestDto;
import com.exercise.listingproject.dto.CreateUserResponseDto;
import com.exercise.listingproject.dto.GetSpecificUserRequestDto;
import com.exercise.listingproject.dto.GetSpecificUserResponseDto;
import com.exercise.listingproject.dto.GetUsersRequestDto;
import com.exercise.listingproject.dto.GetUsersResponseDto;

public interface UserServices {
    GetUsersResponseDto getAllUsers(GetUsersRequestDto requestDto);

    CreateUserResponseDto createUser(CreateUserRequestDto requestDto);

    GetSpecificUserResponseDto getSpecificUser(GetSpecificUserRequestDto requestDto);
}
