package com.exercise.listingproject.dto;

public class CreateUserResponseDto {
    private boolean result;
    private UserDto user;

    public boolean isResult() {
      return result;
    }
    public void setResult(boolean result) {
      this.result = result;
    }
    
    public UserDto getUser() {
      return user;
    }
    public void setUser(UserDto user) {
      this.user = user;
    }
    
}
