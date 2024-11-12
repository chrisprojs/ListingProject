package com.exercise.listingproject.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exercise.listingproject.dto.CreateListingRequestDto;
import com.exercise.listingproject.dto.CreateListingResponseDto;
import com.exercise.listingproject.dto.CreateUserRequestDto;
import com.exercise.listingproject.dto.CreateUserResponseDto;
import com.exercise.listingproject.dto.GetAllListingsPublicAPIResponseDto;
import com.exercise.listingproject.dto.GetAllListingsResponseDto;
import com.exercise.listingproject.dto.GetSpecificUserResponseDto;
import com.exercise.listingproject.dto.ListingPublicAPIDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/public-api")
public class PublicAPIController {
    // Injecting RestTemplate to make HTTP requests to other services
    @Autowired
    private RestTemplate restTemplate;

    @Value("${listingproject.host}")
    private String listingProjectHost;

    // private static final Logger logger = LoggerFactory.getLogger(PublicAPIController.class);

    // private static final ObjectMapper objectMapper = new ObjectMapper();

    // Get Listings (Public API)
    @GetMapping("/listings")
    public GetAllListingsPublicAPIResponseDto getListingsPublicAPI(
            @RequestParam(name = "pageNum", defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(name = "userId", required = false) @Min(1) Integer userId
    ) {
        // Construct URL for fetching listings from the listing service
        String listingsUrl = listingProjectHost + "/listings?pageNum=" + pageNum + "&pageSize=" + pageSize;
        if (userId != null) {
            listingsUrl += "&userId=" + userId;
        }

        // Fetch listings from the listing service
        GetAllListingsResponseDto listingsResponse = restTemplate.getForObject(listingsUrl, GetAllListingsResponseDto.class);

        // Map the fetched listings to a response format including user data
        List<ListingPublicAPIDto> listingPublicAPIDto = listingsResponse.getListings().stream().map(listing -> {
          ListingPublicAPIDto listingWithUser = new ListingPublicAPIDto(listing);
            if (listing.getUserId() != null) {
                // Call /users/{id} API for each listing's userId
                String userUrl = listingProjectHost + "/users/" + listing.getUserId();
                GetSpecificUserResponseDto userResponse = restTemplate.getForObject(userUrl, GetSpecificUserResponseDto.class);
                listingWithUser.setUser(userResponse.getUser());
            }

            return listingWithUser;
        }).collect(Collectors.toList());

        GetAllListingsPublicAPIResponseDto responseDto = new GetAllListingsPublicAPIResponseDto();
        responseDto.setResult(true);
        responseDto.setListings(listingPublicAPIDto);
        return responseDto;
    }

    // Create Users (Public API)
    @PostMapping("/users")
    public CreateUserResponseDto createUserPublicAPI(@Valid @RequestBody CreateUserRequestDto requestDto) {
      String usersUrl = listingProjectHost + "/users"; 

      // Convert the requestDto to an x-www-form-urlencoded format
      String formData = "name=" + requestDto.getName();
      
      // Set headers for x-www-form-urlencoded
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      
      HttpEntity<String> entity = new HttpEntity<>(formData, headers);
      
      // Send POST request with x-www-form-urlencoded data
      CreateUserResponseDto responseDto = restTemplate.exchange(
              usersUrl, HttpMethod.POST, entity, CreateUserResponseDto.class).getBody();

      return responseDto;
    }

    // Create Listings (Public API)
    @PostMapping("/listings")
    public CreateListingResponseDto createListingPublicAPI(@Valid @RequestBody CreateListingRequestDto requestDto) {
      String usersUrl = listingProjectHost + "/listings"; 

      // Convert the requestDto to an x-www-form-urlencoded format
      String formData = "userId=" + requestDto.getUserId() + "&listingType=" +requestDto.getListingType() + "&price="+requestDto.getPrice();

      // Set headers for x-www-form-urlencoded
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      
      HttpEntity<String> entity = new HttpEntity<>(formData, headers);
      
      // Send POST request with x-www-form-urlencoded data
      CreateListingResponseDto responseDto = restTemplate.exchange(
              usersUrl, HttpMethod.POST, entity, CreateListingResponseDto.class).getBody();

      return responseDto;
    }
}
