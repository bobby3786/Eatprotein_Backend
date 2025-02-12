package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.UomDto;
import com.catalog.entity.Uom;
import com.catalog.repository.UomRepository;
import com.catalog.service.IUomService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class UomServiceImpl implements IUomService{
	

	@Autowired
	private UomRepository uomRepository;

	@Override
	public void createUom(UomDto uomDto) {

		Uom uom=new Uom();
		ObjectEntityCheckutil.copyNonNullProperties(uomDto, uom);
		uomRepository.save(uom);
		
	}

	@Override
	public Uom fetchUom(int id) {
	
		Uom uom = uomRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Uom", "id", id)
				);
		
		return uom;
		
	}

	@Override
	public boolean updateUom(UomDto uomDto) {
	
		Uom uom = uomRepository.findById(uomDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("Uom", "id", uomDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(uomDto, uom);
		uomRepository.save(uom);
		
		return true;
	}

	@Override
	public boolean deleteUom(int id) {

		uomRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Uom", "id", id)
				);
		uomRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Uom> fetchAllUoms() {
		
		return uomRepository.findAll();
	}



}
