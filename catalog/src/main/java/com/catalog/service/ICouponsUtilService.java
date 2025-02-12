package com.catalog.service;

import java.util.List;

import com.catalog.dto.CouponsUtilDto;
import com.catalog.entity.CouponsUtil;

public interface ICouponsUtilService {
	
	void createCouponsUtil(CouponsUtilDto couponsUtilDto);
	CouponsUtil fetchCouponsUtil(int id);
	boolean updateCouponsUtil(CouponsUtilDto couponsUtilDto);
	boolean deleteCouponsUtil(int id);
	List<CouponsUtil> fetchAllCouponsUtils();

}
