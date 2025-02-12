package com.catalog.entity;

import java.time.LocalDate;
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
@Table(name = "promocode")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Promocode extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = "longitude")
	private double longitude;
	
	@Column(name = "radius")
	private int radius;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "offer_percentage")
	private String offerPercentage;
	
	@Column(name = "max_limit")
	private  String maxLimit;
	
	@Column(name = "unique_user")
	private String uniqueUser;       //YES , NO
	
	@Column(name = "limit_apply")
	private String limitApply;       //YES , NO
	
	@Column(name = "limit_count")
	private int limitCount;
	
	@Column(name = "min_order_value")
	private int minOrderValue;
	
	@Column(name = "number_of_claims",columnDefinition = "int default 200")
	private int numberOfClaims;
	
	@Column(name = "used_count")
	private int usedCount;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Column(name = "days_limit")
	private  String daysLimit;     //NONE , DURATION
	
	@Column(name = "type")
	private String type;           //ALL , STORE , LOCATION
	
	@Column(name = "terms")
	private String terms;
	

}
