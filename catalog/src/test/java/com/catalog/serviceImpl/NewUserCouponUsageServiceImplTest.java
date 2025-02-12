package com.catalog.serviceImpl;

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

import com.catalog.dto.NewUserCouponUsageDto;
import com.catalog.entity.NewUserCouponUsage;
import com.catalog.repository.NewUserCouponUsageRepository;
import com.catalog.service.impl.NewUserCouponUsageServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class NewUserCouponUsageServiceImplTest {
	
	@Mock
    private NewUserCouponUsageRepository newUserCouponUsageRepository;

    @InjectMocks
    private NewUserCouponUsageServiceImpl newUserCouponUsageService;
    

    @Test
    void testCreateNewUserCouponUsage() {
    	
    	 NewUserCouponUsageDto newUserCouponUsageDto = new NewUserCouponUsageDto();
    	 newUserCouponUsageDto.setPhone("9182291822");
		  newUserCouponUsageDto.setUserId(1);
		  newUserCouponUsageDto.setCouponCode("EATPROTEIN");
        
        
        NewUserCouponUsage newUserCouponUsage = new NewUserCouponUsage();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(newUserCouponUsageDto, newUserCouponUsage))
                        .thenAnswer(invocation -> null);

            when(newUserCouponUsageRepository.save(any(NewUserCouponUsage.class))).thenReturn(newUserCouponUsage);

            newUserCouponUsageService.createNewUserCouponUsage(newUserCouponUsageDto);

            verify(newUserCouponUsageRepository, times(1)).save(any(NewUserCouponUsage.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(newUserCouponUsageDto, newUserCouponUsage), times(1));
        }
    }
    
    @Test
    void testCreateNewUserCouponUsage_NullDto() {
       
        NewUserCouponUsageDto NewUserCouponUsageDto = null;

        assertThrows(IllegalArgumentException.class, () -> newUserCouponUsageService.createNewUserCouponUsage(NewUserCouponUsageDto));
        verify(newUserCouponUsageRepository, never()).save(any(NewUserCouponUsage.class));
    }
    
    @Test
    void testFetchNewUserCouponUsage() {
     
        int id = 1; 

        NewUserCouponUsage newUserCouponUsage = new NewUserCouponUsage();
        newUserCouponUsage.setId(id);
        newUserCouponUsage.setPhone("9182291822");
        newUserCouponUsage.setUserId(1);
		  newUserCouponUsage.setCouponCode("EATPROTEIN");


       when(newUserCouponUsageRepository.findById(id)).thenReturn(Optional.of(newUserCouponUsage));

        NewUserCouponUsage result = newUserCouponUsageService.fetchNewUserCouponUsage(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("9182291822", result.getPhone());
        assertEquals(1, result.getUserId());
        assertEquals("EATPROTEIN", result.getCouponCode());
        verify(newUserCouponUsageRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchNewUserCouponUsageNotFound() {
        int id = 1;

        when(newUserCouponUsageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> newUserCouponUsageService.fetchNewUserCouponUsage(id));
        verify(newUserCouponUsageRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateNewUserCouponUsage() {
        
    	NewUserCouponUsageDto newUserCouponUsageDto = new NewUserCouponUsageDto();
    	newUserCouponUsageDto.setId(1);
    	newUserCouponUsageDto.setPhone("9182291822");
		  newUserCouponUsageDto.setUserId(1);
		  newUserCouponUsageDto.setCouponCode("EATPROTEIN");

        NewUserCouponUsage existingNewUserCouponUsage = new NewUserCouponUsage();
        existingNewUserCouponUsage.setId(1); 
        existingNewUserCouponUsage.setPhone("9182291822");
        existingNewUserCouponUsage.setUserId(1);
        existingNewUserCouponUsage.setCouponCode("EATPROTEIN");

        when(newUserCouponUsageRepository.findById(newUserCouponUsageDto.getId())).thenReturn(Optional.of(existingNewUserCouponUsage));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(newUserCouponUsageDto, existingNewUserCouponUsage))
                        .thenAnswer(invocation -> null);

            when(newUserCouponUsageRepository.save(existingNewUserCouponUsage)).thenReturn(existingNewUserCouponUsage);

            boolean result = newUserCouponUsageService.updateNewUserCouponUsage(newUserCouponUsageDto);

            assertTrue(result);
            verify(newUserCouponUsageRepository, times(1)).findById(newUserCouponUsageDto.getId());
            verify(newUserCouponUsageRepository, times(1)).save(existingNewUserCouponUsage);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(newUserCouponUsageDto, existingNewUserCouponUsage), times(1));
        }
    }


    @Test
    void testUpdateNewUserCouponUsageNotFound() {
        NewUserCouponUsageDto NewUserCouponUsageDto = new NewUserCouponUsageDto();
        NewUserCouponUsageDto.setId(1);

        when(newUserCouponUsageRepository.findById(NewUserCouponUsageDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> newUserCouponUsageService.updateNewUserCouponUsage(NewUserCouponUsageDto));
        verify(newUserCouponUsageRepository, times(1)).findById(NewUserCouponUsageDto.getId());
    }

    @Test
    void testDeleteNewUserCouponUsage() {
        int id = 1;
        NewUserCouponUsage newUserCouponUsage = new NewUserCouponUsage();
        newUserCouponUsage.setId(id);

        when(newUserCouponUsageRepository.findById(id)).thenReturn(Optional.of(newUserCouponUsage));
        doNothing().when(newUserCouponUsageRepository).deleteById(id);

        boolean result = newUserCouponUsageService.deleteNewUserCouponUsage(id);

        assertTrue(result);
        verify(newUserCouponUsageRepository, times(1)).findById(id);
        verify(newUserCouponUsageRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteNewUserCouponUsageNotFound() {
        int id = 1;

        when(newUserCouponUsageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> newUserCouponUsageService.deleteNewUserCouponUsage(id));
        verify(newUserCouponUsageRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllNewUserCouponUsages() {
        List<NewUserCouponUsage> newUserCouponUsageList = new ArrayList<>();
        newUserCouponUsageList.add(new NewUserCouponUsage());
        newUserCouponUsageList.add(new NewUserCouponUsage());

        when(newUserCouponUsageRepository.findAll()).thenReturn(newUserCouponUsageList);

        List<NewUserCouponUsage> result = newUserCouponUsageService.fetchAllNewUserCouponUsages();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(newUserCouponUsageRepository, times(1)).findAll();
    }



}
