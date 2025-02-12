package com.store.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sharedLibrary.entity.BaseEntity;

@Document(collection = "location")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Location extends BaseEntity{
	
	@Id
	private String id;
	
	private String area;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private double latitude;
	
	private double longitude;
	
	private String status;

}
