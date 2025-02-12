package com.store.dto;

import lombok.Data;

@Data
public class ProductPriceDto {
	
	private String id;
	private int productId;
	private int StoreId;
	private String quantity;
	private double qty;
	private String measure;
	private  String minQuality;
	private String maxQuality;
	private String minMeasure;
	private String maxMeasure;
	private String price;
	private int gsp;
	private double offerPrice;
	private float offer;
	private double gspPercentage;
	private double salePrice;
	private float cGst;
	private float sGst;
	private String status;

}
