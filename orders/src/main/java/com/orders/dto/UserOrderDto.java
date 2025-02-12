package com.orders.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import lombok.Data;

@Data
public class UserOrderDto {
	
	private String id;
	private int userId;
	private double totalItems;
	private double cartTotal;
	private double itemsCost;
	private double offerPrice;
	private double itemTotal;
	private double totalTax;
	private double totalRoundoff;
	private int addressId;
	private String paymentType;
	private int orderStatus;
	private String notes;
	private String couponApplied;
	private double couponPercentage;
	private double couponAmount;
	private String couponType;
	private String couponCode;
	private int deliveryCharge;
	private LocalDate slotDate;
	private Time slotStartTime;
	private Time slotEndTime;
	private LocalDate dated;
	private double appAmount;

}
