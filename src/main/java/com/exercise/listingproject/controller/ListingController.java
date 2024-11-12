package com.exercise.listingproject.controller;

import com.exercise.listingproject.dto.CreateListingRequestDto;
import com.exercise.listingproject.dto.CreateListingResponseDto;
import com.exercise.listingproject.dto.GetAllListingsRequestDto;
import com.exercise.listingproject.dto.GetAllListingsResponseDto;
import com.exercise.listingproject.service.ListingServices;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ListingController {
  @Autowired
  private ListingServices services;

  // Get All Listings
  @GetMapping("/listings")
  public GetAllListingsResponseDto getAllListings(
      @RequestParam(name = "pageNum", defaultValue = "1") @Min(1) Integer pageNum,
      @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize,
      @RequestParam(name = "userId", required = false) @Min(1) Integer userId) {
    GetAllListingsRequestDto requestDto = new GetAllListingsRequestDto();
    requestDto.setPageNum(pageNum - 1);
    requestDto.setPageSize(pageSize);
    requestDto.setUserId(userId);

    return services.getAllListings(requestDto);
  }

  // Create Listing
  @PostMapping("/listings")
  public CreateListingResponseDto createListing(@Valid CreateListingRequestDto requestDto) {
    return services.createListing(requestDto);
  }
}
