package com.store.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.store.dto.LocationDto;
import com.store.entity.Location;
import com.store.repository.LocationRepository;


@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {
	
	@Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;
    

    @Test
    void testCreateLocation() {
    	
    	 LocationDto locationDto = new LocationDto();
    	 locationDto.setCity("kavali");
		  locationDto.setState("AP");
        
        
        Location location = new Location();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(locationDto, location))
                        .thenAnswer(invocation -> null);

            when(locationRepository.save(any(Location.class))).thenReturn(location);

            locationService.createLocation(locationDto);

            verify(locationRepository, times(1)).save(any(Location.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(locationDto, location), times(1));
        }
    }
    
    @Test
    void testCreateLocation_NullDto() {
       
        LocationDto locationDto = null;

        assertThrows(IllegalArgumentException.class, () -> locationService.createLocation(locationDto));
        verify(locationRepository, never()).save(any(Location.class));
    }
    
    @Test
    void testFetchLocation() {
     
        String id = "1"; 

        Location location = new Location();
        location.setId(id);
        location.setCity("kavali");
        location.setState("AP");


       when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        Location result = locationService.fetchLocation(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("kavali", result.getCity()); 
        assertEquals("AP", result.getState()); 
        verify(locationRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchLocationNotFound() {
        String id = "1";

        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> locationService.fetchLocation(id));
        verify(locationRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateLocation() {
        
    	LocationDto locationDto = new LocationDto();
    	locationDto.setId("1");
    	locationDto.setCity("kavali");
		  locationDto.setState("AP");

        Location existingLocation = new Location();
        existingLocation.setId("1"); 
        existingLocation.setCity("kavali");
        existingLocation.setState("AP");

        when(locationRepository.findById(locationDto.getId())).thenReturn(Optional.of(existingLocation));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(locationDto, existingLocation))
                        .thenAnswer(invocation -> null);

            when(locationRepository.save(existingLocation)).thenReturn(existingLocation);

            boolean result = locationService.updateLocation(locationDto);

            assertTrue(result);
            verify(locationRepository, times(1)).findById(locationDto.getId());
            verify(locationRepository, times(1)).save(existingLocation);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(locationDto, existingLocation), times(1));
        }
    }


    @Test
    void testUpdateLocationNotFound() {
        LocationDto locationDto = new LocationDto();
        locationDto.setId("1");

        when(locationRepository.findById(locationDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> locationService.updateLocation(locationDto));
        verify(locationRepository, times(1)).findById(locationDto.getId());
    }

    @Test
    void testDeleteLocation() {
        String id = "1";
        Location location = new Location();
        location.setId(id);

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        doNothing().when(locationRepository).deleteById(id);

        boolean result = locationService.deleteLocation(id);

        assertTrue(result);
        verify(locationRepository, times(1)).findById(id);
        verify(locationRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteLocationNotFound() {
        String id = "1";

        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> locationService.deleteLocation(id));
        verify(locationRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllLocations() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location());
        locationList.add(new Location());

        when(locationRepository.findAll()).thenReturn(locationList);

        List<Location> result = locationService.fetchAllLocations();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(locationRepository, times(1)).findAll();
    }


}
