package com.orders.entity;

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

@Document(collection = "order_item")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity{
	
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "price_id")
	private int priceId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "measure")
	private String measure;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "c_gst")
	private double cGst;
	
	@Column(name = "s_gst")
	private double sGst;
	
	@Column(name = "total_tax")
	private double totalTax;
	
	@Column(name = "offer_price")
	private double offerPrice;
	
	@Column(name = "item_total")
	private double itemTotal;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "app_share")
	private double appShare;
	
	@Column(name = "f_share")
	private double fShare;
	
	@Column(name = "store_amount")
	private double storeAmount;

}
