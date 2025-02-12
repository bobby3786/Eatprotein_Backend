package com.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.dto.UniqueCodeDto;
import com.authentication.entity.UniqueCode;
import com.authentication.repository.UniqueCodeRepository;
import com.authentication.service.IUniqueCodeService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UniqueCodeServiceImpl implements IUniqueCodeService{
	
	@Autowired
	private UniqueCodeRepository uniqueCodeRepository;

	@Override
	public void createUniqueCode(UniqueCodeDto uniqueCodeDto) {

		UniqueCode uniqueCode=new UniqueCode();
		ObjectEntityCheckutil.copyNonNullProperties(uniqueCodeDto, uniqueCode);
		uniqueCodeRepository.save(uniqueCode);
		
	}

	@Override
	public UniqueCode fetchUniqueCode(int id) {
	
		UniqueCode uniqueCode = uniqueCodeRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("UniqueCode", "id", id)
				);
		
		return uniqueCode;
		
	}

	@Override
	public boolean updateUniqueCode(UniqueCodeDto uniqueCodeDto) {
	
		UniqueCode uniqueCode = uniqueCodeRepository.findById(uniqueCodeDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("uniqueCode", "id", uniqueCodeDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(uniqueCodeDto, uniqueCode);
		uniqueCodeRepository.save(uniqueCode);
		
		return true;
	}

	@Override
	public boolean deleteUniqueCode(int id) {

		uniqueCodeRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("UniqueCode", "id", id)
				);
		uniqueCodeRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UniqueCode> fetchAllUniqueCodes() {
		
		return uniqueCodeRepository.findAll();
	}



}
