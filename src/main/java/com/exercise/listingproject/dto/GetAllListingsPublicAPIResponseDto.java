package com.exercise.listingproject.dto;

import java.util.List;

public class GetAllListingsPublicAPIResponseDto {
    private boolean result;
    private List<ListingPublicAPIDto> listings;

    public boolean isResult() {
      return result;
    }
    public void setResult(boolean result) {
      this.result = result;
    }
    public List<ListingPublicAPIDto> getListings() {
      return listings;
    }
    public void setListings(List<ListingPublicAPIDto> listings) {
      this.listings = listings;
    }
}
