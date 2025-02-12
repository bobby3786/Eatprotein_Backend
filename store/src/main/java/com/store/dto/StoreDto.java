package com.store.dto;

import lombok.Data;

@Data
public class StoreDto {
	
	private String id;
	private int sectionId;
	private int categoryId;
	private int franchiseId;
	private String name;
	private String imagePath;
	private String city;
	private int locationId;
	private String locationName;
	private String address;
	private String landmark;
	private double latitude;
	private double longitude;
	private int radius;
	private double deliveryCharge;
	private double freeDeliveryMin;
	private int carryBag;
	private String contactName;
	private String contactOne;
	private String contactTwo;
	private String mail;
	private int viewsCount;
	private float avgRating;
	private int wishlistCount;
	private int minPrice;
	private int maxPrice;
	private int maxOffer;
	private int maxOfferProductId;
	private String status;
	private String servingStatus;
	private String timeSlot;
	private String deliveryTime;
	private String isVeg;
	private String gst;
	private String fssai;
	private String createdBy;

}
