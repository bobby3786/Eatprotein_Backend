package com.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sharedLibrary.entity.BaseEntity;

@Document(collection = "product_price")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductPrice extends BaseEntity{
	
	//store
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "store_id")
	private int StoreId;
	
	@Column(name = "quantity")
	private String quantity;
	
	@Column(name = "qty")
	private double qty;
	
	@Column(name = "measure")
	private String measure;
	
	@Column(name = "min_quality")
	private  String minQuality;
	
	@Column(name = "max_quality")
	private String maxQuality;
	
	@Column(name = "min_measure")
	private String minMeasure;
	
	@Column(name = "max_measure")
	private String maxMeasure;
	
	@Column(name = "price")
	private String price;
	
	@Column(name = "gsp")
	private int gsp;
	
	@Column(name = "offer_price")
	private double offerPrice;
	
	@Column(name = "offer")
	private float offer;
	
	@Column(name = "gsp_percentage")
	private double gspPercentage;
	
	@Column(name = "sale_price")
	private double salePrice;
	
	@Column(name = "c_gst")
	private float cGst;
	
	@Column(name = "s_gst")
	private float sGst;
	
	@Column(name = "status")
	private String status;


}
