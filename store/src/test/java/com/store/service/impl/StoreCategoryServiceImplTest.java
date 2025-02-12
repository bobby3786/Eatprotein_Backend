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
import com.store.dto.StoreCategoryDto;
import com.store.entity.StoreCategory;
import com.store.repository.StoreCategoryRepository;

@ExtendWith(MockitoExtension.class)
public class StoreCategoryServiceImplTest {
	
	@Mock
    private StoreCategoryRepository storeCategoryRepository;

    @InjectMocks
    private StoreCategoryServiceImpl storeCategoryService;
    

    @Test
    void testCreateStoreCategory() {
    	
    	 StoreCategoryDto storeCategoryDto = new StoreCategoryDto();
    	 storeCategoryDto.setStatus("active");
		  storeCategoryDto.setCategoryId(1);
        
        
        StoreCategory storeCategory = new StoreCategory();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeCategoryDto, storeCategory))
                        .thenAnswer(invocation -> null);

            when(storeCategoryRepository.save(any(StoreCategory.class))).thenReturn(storeCategory);

            storeCategoryService.createStoreCategory(storeCategoryDto);

            verify(storeCategoryRepository, times(1)).save(any(StoreCategory.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeCategoryDto, storeCategory), times(1));
        }
    }
    
    @Test
    void testCreateStoreCategory_NullDto() {
       
        StoreCategoryDto storeCategoryDto = null;

        assertThrows(IllegalArgumentException.class, () -> storeCategoryService.createStoreCategory(storeCategoryDto));
        verify(storeCategoryRepository, never()).save(any(StoreCategory.class));
    }
    
    @Test
    void testFetchStoreCategory() {
     
        String id = "1"; 

        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setId(id);
        storeCategory.setStatus("active");
        storeCategory.setCategoryId(1);


       when(storeCategoryRepository.findById(id)).thenReturn(Optional.of(storeCategory));

        StoreCategory result = storeCategoryService.fetchStoreCategory(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("active", result.getStatus()); 
        assertEquals(1, result.getCategoryId()); 
        verify(storeCategoryRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchStoreCategoryNotFound() {
        String id = "1";

        when(storeCategoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeCategoryService.fetchStoreCategory(id));
        verify(storeCategoryRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStoreCategory() {
        
    	StoreCategoryDto storeCategoryDto = new StoreCategoryDto();
    	storeCategoryDto.setId("1");
    	storeCategoryDto.setStatus("active");
		  storeCategoryDto.setCategoryId(1);

        StoreCategory existingStoreCategory = new StoreCategory();
        existingStoreCategory.setId("1"); 
        existingStoreCategory.setStatus("active");
        existingStoreCategory.setCategoryId(1);

        when(storeCategoryRepository.findById(storeCategoryDto.getId())).thenReturn(Optional.of(existingStoreCategory));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeCategoryDto, existingStoreCategory))
                        .thenAnswer(invocation -> null);

            when(storeCategoryRepository.save(existingStoreCategory)).thenReturn(existingStoreCategory);

            boolean result = storeCategoryService.updateStoreCategory(storeCategoryDto);

            assertTrue(result);
            verify(storeCategoryRepository, times(1)).findById(storeCategoryDto.getId());
            verify(storeCategoryRepository, times(1)).save(existingStoreCategory);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeCategoryDto, existingStoreCategory), times(1));
        }
    }


    @Test
    void testUpdateStoreCategoryNotFound() {
        StoreCategoryDto storeCategoryDto = new StoreCategoryDto();
        storeCategoryDto.setId("1");

        when(storeCategoryRepository.findById(storeCategoryDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeCategoryService.updateStoreCategory(storeCategoryDto));
        verify(storeCategoryRepository, times(1)).findById(storeCategoryDto.getId());
    }

    @Test
    void testDeleteStoreCategory() {
        String id = "1";
        StoreCategory storeCategory = new StoreCategory();
        storeCategory.setId(id);

        when(storeCategoryRepository.findById(id)).thenReturn(Optional.of(storeCategory));
        doNothing().when(storeCategoryRepository).deleteById(id);

        boolean result = storeCategoryService.deleteStoreCategory(id);

        assertTrue(result);
        verify(storeCategoryRepository, times(1)).findById(id);
        verify(storeCategoryRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStoreCategoryNotFound() {
        String id = "1";

        when(storeCategoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeCategoryService.deleteStoreCategory(id));
        verify(storeCategoryRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllStoreCategorys() {
        List<StoreCategory> storeCategoryList = new ArrayList<>();
        storeCategoryList.add(new StoreCategory());
        storeCategoryList.add(new StoreCategory());

        when(storeCategoryRepository.findAll()).thenReturn(storeCategoryList);

        List<StoreCategory> result = storeCategoryService.fetchAllStoreCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(storeCategoryRepository, times(1)).findAll();
    }


}
