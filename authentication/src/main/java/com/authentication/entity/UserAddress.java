package com.authentication.entity;

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
import com.sharedLibrary.entity.BaseEntity;
@Entity
@Table(name = "user_address")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "flat_apartment")
	private String flatApartment;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "place_id")
	private String placeId;
	
	@Column(name = "pincode")
	private String pincode;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = "longitude")
	private double longitude;
	
	@Column(name = "status")
	private String status;

}
