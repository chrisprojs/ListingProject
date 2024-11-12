package com.exercise.listingproject.service.impl;

import com.exercise.listingproject.dto.CreateListingRequestDto;
import com.exercise.listingproject.dto.CreateListingResponseDto;

import com.exercise.listingproject.dto.GetAllListingsRequestDto;
import com.exercise.listingproject.dto.GetAllListingsResponseDto;
import com.exercise.listingproject.dto.ListingDto;
import com.exercise.listingproject.entity.Listing;
import com.exercise.listingproject.entity.User;
import com.exercise.listingproject.repository.ListingRepository;
import com.exercise.listingproject.repository.UserRepository;
import com.exercise.listingproject.service.ListingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingServicesImpl implements ListingServices {
  @Autowired
  private ListingRepository listingRepository;

  @Autowired
  private UserRepository userRepository;

  // private static final Logger logger =
  // LoggerFactory.getLogger(ListingProjectImpl.class);

  // private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public GetAllListingsResponseDto getAllListings(GetAllListingsRequestDto requestDto) {
    // Paging Listings and sort descending by createdAt
    PageRequest pageRequest = PageRequest.of(requestDto.getPageNum(),
        requestDto.getPageSize(), Sort.by("createdAt").descending());

    List<Listing> result;

    // If userId is provided, filter listings by user ID
    if (requestDto.getUserId() != null) {
      result = listingRepository.findByUserId(requestDto.getUserId(), pageRequest);
    } else {
      result = listingRepository.findAll(pageRequest).getContent();
    }

    // Convert listings to DTOs and return in response
    List<ListingDto> listingDtoList = result.stream()
        .map(this::convertListingToListingDto)
        .collect(Collectors.toList());

    GetAllListingsResponseDto responseDto = new GetAllListingsResponseDto();
    responseDto.setResult(true);
    responseDto.setListings(listingDtoList);

    return responseDto;
  }

  @Override
  public CreateListingResponseDto createListing(CreateListingRequestDto requestDto) {
    Listing listing = new Listing();
    listing.setPrice(requestDto.getPrice());

    // Associate listing with user
    Optional<User> userOptional = userRepository.findById(requestDto.getUserId());
    if (userOptional.isPresent()) {
      listing.setUser(userOptional.get());
    } else {
      throw new RuntimeException("User Id is not exist: " + requestDto.getUserId());
    }
    listing.setListingType(requestDto.getListingType());

    // Set timestamps
    final Long timestampInMicroSecond = ImplFunction.nowInEpochMicroSecond();
    listing.setCreatedAt(timestampInMicroSecond);
    listing.setUpdatedAt(timestampInMicroSecond);

    // Save Listing
    listingRepository.save(listing);

    CreateListingResponseDto responseDto = new CreateListingResponseDto();
    responseDto.setResult(true);
    responseDto.setListing(convertListingToListingDto(listing));

    return responseDto;
  }

  // Convert Listing entity to ListingDto
  private ListingDto convertListingToListingDto(Listing listing) {
    ListingDto listingDto = new ListingDto();
    listingDto.setId(listing.getId());
    listingDto.setListingType(listing.getListingType());
    listingDto.setPrice(listing.getPrice());
    listingDto.setCreatedAt(listing.getCreatedAt());
    listingDto.setUpdatedAt(listing.getUpdatedAt());

    // Set userId explicitly from the user entity
    if (listing.getUser() != null) {
      listingDto.setUserId(listing.getUser().getId());
    }

    return listingDto;
  }
}
