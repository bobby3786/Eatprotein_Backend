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
import com.store.dto.StoreRatingDto;
import com.store.entity.StoreRating;
import com.store.repository.StoreRatingRepository;


@ExtendWith(MockitoExtension.class)
public class StoreRatingServiceImplTest {
	
	@Mock
    private StoreRatingRepository storeRatingRepository;

    @InjectMocks
    private StoreRatingServiceImpl storeRatingService;
    

    @Test
    void testCreateStoreRating() {
    	
    	 StoreRatingDto storeRatingDto = new StoreRatingDto();
    	 storeRatingDto.setComments("good");
		  storeRatingDto.setRating(5);
        
        
        StoreRating storeRating = new StoreRating();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeRatingDto, storeRating))
                        .thenAnswer(invocation -> null);

            when(storeRatingRepository.save(any(StoreRating.class))).thenReturn(storeRating);

            storeRatingService.createStoreRating(storeRatingDto);

            verify(storeRatingRepository, times(1)).save(any(StoreRating.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeRatingDto, storeRating), times(1));
        }
    }
    
    @Test
    void testCreateStoreRating_NullDto() {
       
        StoreRatingDto storeRatingDto = null;

        assertThrows(IllegalArgumentException.class, () -> storeRatingService.createStoreRating(storeRatingDto));
        verify(storeRatingRepository, never()).save(any(StoreRating.class));
    }
    
    @Test
    void testFetchStoreRating() {
     
        String id = "1"; 

        StoreRating storeRating = new StoreRating();
        storeRating.setId(id);
        storeRating.setComments("good");
        storeRating.setRating(5);


       when(storeRatingRepository.findById(id)).thenReturn(Optional.of(storeRating));

        StoreRating result = storeRatingService.fetchStoreRating(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("good", result.getComments()); 
        assertEquals(5, result.getRating()); 
        verify(storeRatingRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchStoreRatingNotFound() {
        String id = "1";

        when(storeRatingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeRatingService.fetchStoreRating(id));
        verify(storeRatingRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStoreRating() {
        
    	StoreRatingDto storeRatingDto = new StoreRatingDto();
    	storeRatingDto.setId("1");
    	 storeRatingDto.setComments("good");
		  storeRatingDto.setRating(5);

        StoreRating existingStoreRating = new StoreRating();
        existingStoreRating.setId("1"); 
        existingStoreRating.setComments("good");
        existingStoreRating.setRating(5);

        when(storeRatingRepository.findById(storeRatingDto.getId())).thenReturn(Optional.of(existingStoreRating));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeRatingDto, existingStoreRating))
                        .thenAnswer(invocation -> null);

            when(storeRatingRepository.save(existingStoreRating)).thenReturn(existingStoreRating);

            boolean result = storeRatingService.updateStoreRating(storeRatingDto);

            assertTrue(result);
            verify(storeRatingRepository, times(1)).findById(storeRatingDto.getId());
            verify(storeRatingRepository, times(1)).save(existingStoreRating);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeRatingDto, existingStoreRating), times(1));
        }
    }


    @Test
    void testUpdateStoreRatingNotFound() {
        StoreRatingDto storeRatingDto = new StoreRatingDto();
        storeRatingDto.setId("1");

        when(storeRatingRepository.findById(storeRatingDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeRatingService.updateStoreRating(storeRatingDto));
        verify(storeRatingRepository, times(1)).findById(storeRatingDto.getId());
    }

    @Test
    void testDeleteStoreRating() {
        String id = "1";
        StoreRating storeRating = new StoreRating();
        storeRating.setId(id);

        when(storeRatingRepository.findById(id)).thenReturn(Optional.of(storeRating));
        doNothing().when(storeRatingRepository).deleteById(id);

        boolean result = storeRatingService.deleteStoreRating(id);

        assertTrue(result);
        verify(storeRatingRepository, times(1)).findById(id);
        verify(storeRatingRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStoreRatingNotFound() {
        String id = "1";

        when(storeRatingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeRatingService.deleteStoreRating(id));
        verify(storeRatingRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllStoreRatings() {
        List<StoreRating> storeRatingList = new ArrayList<>();
        storeRatingList.add(new StoreRating());
        storeRatingList.add(new StoreRating());

        when(storeRatingRepository.findAll()).thenReturn(storeRatingList);

        List<StoreRating> result = storeRatingService.fetchAllStoreRatings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(storeRatingRepository, times(1)).findAll();
    }


}
