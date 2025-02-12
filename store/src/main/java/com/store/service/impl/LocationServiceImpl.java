package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharedLibrary.exception.ResourceNotFoundException1;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.dto.LocationDto;
import com.store.entity.Location;
import com.store.repository.LocationRepository;
import com.store.service.ILocationService;

@Service
public class LocationServiceImpl implements ILocationService{
	
	@Autowired
	private LocationRepository locationRepository;

	@Override
	public void createLocation(LocationDto locationDto) {

		Location location=new Location();
		ObjectEntityCheckutil.copyNonNullProperties(locationDto, location);
		locationRepository.save(location);
		
	}

	@Override
	public Location fetchLocation(String id) {
	
		Location location = locationRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("location", "id", id)
				);
		
		return location;
		
	}

	@Override
	public boolean updateLocation(LocationDto locationDto) {
	
		Location location = locationRepository.findById(locationDto.getId()).orElseThrow(
				()-> new ResourceNotFoundException1("orderItem", "id", locationDto.getId())
				);
		
		ObjectEntityCheckutil.copyNonNullProperties(locationDto, location);
		locationRepository.save(location);
		
		return true;
	}

	@Override
	public boolean deleteLocation(String id) {

		locationRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException1("Location", "id", id)
				);
		locationRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Location> fetchAllLocations() {
		
		return locationRepository.findAll();
	}



}
