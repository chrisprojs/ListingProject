package com.exercise.listingproject.dto;

public class ListingPublicAPIDto {
	private Integer id;
	private String listingType;
	private Integer price;
	private Long createdAt;
	private Long updatedAt;
	private UserDto user;

	public ListingPublicAPIDto(ListingDto listing) {
		this.id = listing.getId();
		this.listingType = listing.getListingType();
		this.price = listing.getPrice();
		this.createdAt = listing.getCreatedAt();
		this.updatedAt = listing.getUpdatedAt();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getListingType() {
		return listingType;
	}

	public void setListingType(String listingType) {
		this.listingType = listingType;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
