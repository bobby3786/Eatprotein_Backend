package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.CouponsUtilDto;
import com.catalog.entity.CouponsUtil;
import com.catalog.repository.CouponsUtilRepository;
import com.catalog.service.ICouponsUtilService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class CouponsUtilServiceImpl implements ICouponsUtilService {
	
	@Autowired
	private CouponsUtilRepository couponsUtilRepository;

	@Override
	public void createCouponsUtil(CouponsUtilDto couponsUtilDto) {

		CouponsUtil couponsUtil=new CouponsUtil();
		ObjectEntityCheckutil.copyNonNullProperties(couponsUtilDto, couponsUtil);
		couponsUtilRepository.save(couponsUtil);
		
	}

	@Override
	public CouponsUtil fetchCouponsUtil(int id) {
	
		CouponsUtil couponsUtil = couponsUtilRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("couponsUtil", "id", id)
				);
		
		return couponsUtil;
		
	}

	@Override
	public boolean updateCouponsUtil(CouponsUtilDto couponsUtilDto) {
	
		CouponsUtil couponsUtil = couponsUtilRepository.findById(couponsUtilDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("couponsUtil", "id", couponsUtilDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(couponsUtilDto, couponsUtil);
		couponsUtilRepository.save(couponsUtil);
		
		return true;
	}

	@Override
	public boolean deleteCouponsUtil(int id) {

		couponsUtilRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("couponsUtil", "id", id)
				);
		couponsUtilRepository.deleteById(id);
		return true;
	}

	@Override
	public List<CouponsUtil> fetchAllCouponsUtils() {
		
		return couponsUtilRepository.findAll();
	}



}
