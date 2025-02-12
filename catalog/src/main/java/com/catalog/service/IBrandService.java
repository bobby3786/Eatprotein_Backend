package com.catalog.service;

import java.util.List;

import com.catalog.dto.BrandDto;
import com.catalog.entity.Brand;

public interface IBrandService {
	
	void createBrand(BrandDto brandDto);
	Brand fetchBrand(int id);
	boolean updateBrand(BrandDto brandDto);
	boolean deleteBrand(int id);
	List<Brand> fetchAllBrands();


}
