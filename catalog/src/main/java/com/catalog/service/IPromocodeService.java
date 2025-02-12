package com.catalog.service;

import java.util.List;

import com.catalog.dto.PromocodeDto;
import com.catalog.entity.Promocode;

public interface IPromocodeService {
	
	void createPromocode(PromocodeDto promocodeDto);
	Promocode fetchPromocode(int id);
	boolean updatePromocode(PromocodeDto promocodeDto);
	boolean deletePromocode(int id);
	List<Promocode> fetchAllPromocodes();

}
