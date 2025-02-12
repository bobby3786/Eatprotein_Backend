package com.catalog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalog.dto.PromocodeDto;
import com.catalog.entity.Promocode;
import com.catalog.repository.PromocodeRepository;
import com.catalog.service.IPromocodeService;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@Service
public class PromocodeServiceImpl implements IPromocodeService{
	
	@Autowired
	private PromocodeRepository promocodeRepsoitory;

	@Override
	public void createPromocode(PromocodeDto promocodeDto) {

		Promocode promocode=new Promocode();
		ObjectEntityCheckutil.copyNonNullProperties(promocodeDto, promocode);
		promocodeRepsoitory.save(promocode);
		
	}

	@Override
	public Promocode fetchPromocode(int id) {
	
		Promocode promocode = promocodeRepsoitory.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("promocode", "id", id)
				);
		
		return promocode;
		
	}

	@Override
	public boolean updatePromocode(PromocodeDto promocodeDto) {
	
		Promocode promocode = promocodeRepsoitory.findById(promocodeDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException("promocode", "id", promocodeDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(promocodeDto, promocode);
		promocodeRepsoitory.save(promocode);
		
		return true;
	}

	@Override
	public boolean deletePromocode(int id) {

		promocodeRepsoitory.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("promocode", "id", id)
				);
		promocodeRepsoitory.deleteById(id);
		return true;
	}

	@Override
	public List<Promocode> fetchAllPromocodes() {
		
		return promocodeRepsoitory.findAll();
	}



}
