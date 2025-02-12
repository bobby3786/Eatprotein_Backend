package com.apputil.dto;

import lombok.Data;

@Data
public class BannerStoreDto {
	
	
	    private int id;
	    private int storeId;
	    private String name;
	    private String description;
	    private  String webUrl;
	    private String imagePath;
	    private String  type;
	    private String status;
	    private double latitude;
	    private double longitude;

}
