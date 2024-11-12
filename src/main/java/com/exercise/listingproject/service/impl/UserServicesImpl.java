package com.exercise.listingproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.exercise.listingproject.dto.CreateUserRequestDto;
import com.exercise.listingproject.dto.CreateUserResponseDto;
import com.exercise.listingproject.dto.GetSpecificUserRequestDto;
import com.exercise.listingproject.dto.GetSpecificUserResponseDto;
import com.exercise.listingproject.dto.GetUsersRequestDto;
import com.exercise.listingproject.dto.GetUsersResponseDto;
import com.exercise.listingproject.dto.UserDto;
import com.exercise.listingproject.entity.User;
import com.exercise.listingproject.repository.UserRepository;
import com.exercise.listingproject.service.UserServices;

@Service
public class UserServicesImpl implements UserServices {
  @Autowired
  private UserRepository userRepository;

  @Override
  public GetUsersResponseDto getAllUsers(GetUsersRequestDto requestDto) {
    // Paging Users and sort descending by createdAt
    PageRequest pageRequest = PageRequest.of(requestDto.getPageNum(),
        requestDto.getPageSize(), Sort.by("createdAt").descending());

    List<User> result = userRepository.findAll(pageRequest).getContent();

    // Convert users to DTOs
    List<UserDto> userDtoList = result.stream()
        .map(this::convertUserToUserDto)
        .collect(Collectors.toList());

    GetUsersResponseDto responseDto = new GetUsersResponseDto();
    responseDto.setResult(true);
    responseDto.setUsers(userDtoList);

    return responseDto;
  }

  @Override
  public CreateUserResponseDto createUser(CreateUserRequestDto requestDto) {
    User user = new User();
    user.setName(requestDto.getName());

    // Set timestamps
    final Long timestampInMicroSecond = ImplFunction.nowInEpochMicroSecond();
    user.setCreatedAt(timestampInMicroSecond);
    user.setUpdatedAt(timestampInMicroSecond);

    // Save User
    userRepository.save(user);

    CreateUserResponseDto responseDto = new CreateUserResponseDto();
    responseDto.setResult(true);
    responseDto.setUser(convertUserToUserDto(user));

    return responseDto;
  }

  @Override
  public GetSpecificUserResponseDto getSpecificUser(GetSpecificUserRequestDto requestDto) {
    // Find Specific User by id
    User result = userRepository.findById(requestDto.getId()).get();

    UserDto userDto = convertUserToUserDto(result);

    GetSpecificUserResponseDto responseDto = new GetSpecificUserResponseDto();
    responseDto.setResult(true);
    responseDto.setUser(userDto);

    return responseDto;
  }

  // Convert User entity to UserDto
  private UserDto convertUserToUserDto(User user) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(user, userDto);

    return userDto;
  }
}
