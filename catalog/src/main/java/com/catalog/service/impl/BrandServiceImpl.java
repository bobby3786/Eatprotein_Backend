package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.BrandDto;
import com.catalog.entity.Brand;
import com.catalog.repository.BrandRepository;
import com.catalog.service.IBrandService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class BrandServiceImpl implements IBrandService{
	
	@Autowired
	private BrandRepository brandRepository;

	@Override
	public void createBrand(BrandDto brandDto) {

		Brand brand=new Brand();
		ObjectEntityCheckutil.copyNonNullProperties(brandDto, brand);
		brandRepository.save(brand);
		
	}

	@Override
	public Brand fetchBrand(int id) {
	
		Brand brand = brandRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("brand", "id", id)
				);
		
		return brand;
		
	}

	@Override
	public boolean updateBrand(BrandDto brandDto) {
	
		Brand brand = brandRepository.findById(brandDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("brand", "id", brandDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(brandDto, brand);
		brandRepository.save(brand);
		
		return true;
	}

	@Override
	public boolean deleteBrand(int id) {

		brandRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("brand", "id", id)
				);
		brandRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Brand> fetchAllBrands() {
		
		return brandRepository.findAll();
	}


}
