package com.authentication.service;

import java.util.List;

import com.authentication.dto.UniqueCodeDto;
import com.authentication.entity.UniqueCode;

public interface IUniqueCodeService {
	
	void createUniqueCode(UniqueCodeDto uniqueCode);
	UniqueCode fetchUniqueCode(int id);
	boolean updateUniqueCode(UniqueCodeDto uniqueCode);
	boolean deleteUniqueCode(int id);
	List<UniqueCode> fetchAllUniqueCodes();

}
