package com.catalog.service;

import java.util.List;

import com.catalog.dto.UomDto;
import com.catalog.entity.Uom;

public interface IUomService {
	
	void createUom(UomDto uomDto);
	Uom fetchUom(int id);
	boolean updateUom(UomDto uomDto);
	boolean deleteUom(int id);
	List<Uom> fetchAllUoms();

}
