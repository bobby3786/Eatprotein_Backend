package com.store.service;

import java.util.List;

import com.store.dto.LocationDto;
import com.store.entity.Location;

public interface ILocationService {
	
	void createLocation(LocationDto locationDto);
	Location fetchLocation(String id);
	boolean updateLocation(LocationDto locationDto);
	boolean deleteLocation(String id);
	List<Location> fetchAllLocations();

}
