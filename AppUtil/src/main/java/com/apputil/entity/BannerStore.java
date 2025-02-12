package com.apputil.entity;

import com.sharedLibrary.entity.BaseEntity;

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

@Entity
@Table(name = "banner_store")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BannerStore extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
	
	@Column(name = "store_id")
    private int storeId;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "description")
    private String description;
	
	
	@Column(name = "web_url")
    private  String webUrl;
	
	@Column(name = "image_path")
    private String imagePath;
	
	@Column(name = "type")
    private String  type;  //'SMALL','LARGE','MEDIUM','WEB'
	
	@Column(name = "status")
    private String status;   //'ACTIVE','DISABLED'
	
	@Column(name = "latitude")
    private double latitude;
	
	@Column(name = "longitude")
    private double longitude;
    

}
