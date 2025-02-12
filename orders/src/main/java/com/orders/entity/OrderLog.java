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

@Document(collection = "order_log")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderLog  extends BaseEntity{
	
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "status_text")
	private String statusText;
	
	@Column(name = "updated_by")
	private int updatedBy;
	
	@Column(name = "notes")
	private String notes;
	

}
