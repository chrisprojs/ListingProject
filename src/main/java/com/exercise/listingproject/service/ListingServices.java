package com.exercise.listingproject.service;

import com.exercise.listingproject.dto.CreateListingRequestDto;
import com.exercise.listingproject.dto.CreateListingResponseDto;
import com.exercise.listingproject.dto.GetAllListingsRequestDto;
import com.exercise.listingproject.dto.GetAllListingsResponseDto;

public interface ListingServices {
  GetAllListingsResponseDto getAllListings(GetAllListingsRequestDto requestDto);

  CreateListingResponseDto createListing(CreateListingRequestDto requestDto);
}
