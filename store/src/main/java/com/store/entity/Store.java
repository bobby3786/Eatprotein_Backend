package com.store.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sharedLibrary.entity.BaseEntity;
@Document(collection = "store")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity{
	
	@Id
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
	
	private int createdBy;

}