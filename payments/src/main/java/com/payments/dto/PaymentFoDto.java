package com.payments.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class PaymentFoDto {
	
	private int id;
	private LocalDate dated;
	private int foId;
	private int orderId;
	private double orderAmount;
	private double foPayment;
	private double totalAppShare;
	private double appPayment;
	private int storeId;
	private double storePayment;
	private int dbId;
	private double deliveryCharges;
	private double actualDbCharge;
	private String status;
	private int couponCost;
	private int packageCharge;
	private int platformFee;
	private double finalAppAmount;
	private String comments;

}
