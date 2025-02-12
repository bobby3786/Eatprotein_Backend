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
import com.store.dto.StoreProductDto;
import com.store.entity.StoreProduct;
import com.store.repository.StoreProductRepository;


@ExtendWith(MockitoExtension.class)
public class StoreProductServiceImplTest {
	
	@Mock
    private StoreProductRepository storeProductRepository;

    @InjectMocks
    private StoreProductServiceImpl storeProductService;
    

    @Test
    void testCreateStoreProduct() {
    	
    	 StoreProductDto storeProductDto = new StoreProductDto();
    	 storeProductDto.setAvgRating(5);
		  storeProductDto.setStoreId(1);
        
        
        StoreProduct storeProduct = new StoreProduct();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeProductDto, storeProduct))
                        .thenAnswer(invocation -> null);

            when(storeProductRepository.save(any(StoreProduct.class))).thenReturn(storeProduct);

            storeProductService.createStoreProduct(storeProductDto);

            verify(storeProductRepository, times(1)).save(any(StoreProduct.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeProductDto, storeProduct), times(1));
        }
    }
    
    @Test
    void testCreateStoreProduct_NullDto() {
       
        StoreProductDto storeProductDto = null;

        assertThrows(IllegalArgumentException.class, () -> storeProductService.createStoreProduct(storeProductDto));
        verify(storeProductRepository, never()).save(any(StoreProduct.class));
    }
    
    @Test
    void testFetchStoreProduct() {
     
        String id = "1"; 

        StoreProduct storeProduct = new StoreProduct();
        storeProduct.setId(id);
        storeProduct.setAvgRating(5);
        storeProduct.setStoreId(1);


       when(storeProductRepository.findById(id)).thenReturn(Optional.of(storeProduct));

        StoreProduct result = storeProductService.fetchStoreProduct(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(5, result.getAvgRating()); 
        assertEquals(1, result.getStoreId()); 
        verify(storeProductRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchStoreProductNotFound() {
        String id = "1";

        when(storeProductRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeProductService.fetchStoreProduct(id));
        verify(storeProductRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateStoreProduct() {
        
    	StoreProductDto storeProductDto = new StoreProductDto();
    	storeProductDto.setId("1");
    	storeProductDto.setAvgRating(5);
		  storeProductDto.setStoreId(1);

        StoreProduct existingStoreProduct = new StoreProduct();
        existingStoreProduct.setId("1"); 
        existingStoreProduct.setAvgRating(5);
        existingStoreProduct.setStoreId(1);

        when(storeProductRepository.findById(storeProductDto.getId())).thenReturn(Optional.of(existingStoreProduct));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(storeProductDto, existingStoreProduct))
                        .thenAnswer(invocation -> null);

            when(storeProductRepository.save(existingStoreProduct)).thenReturn(existingStoreProduct);

            boolean result = storeProductService.updateStoreProduct(storeProductDto);

            assertTrue(result);
            verify(storeProductRepository, times(1)).findById(storeProductDto.getId());
            verify(storeProductRepository, times(1)).save(existingStoreProduct);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(storeProductDto, existingStoreProduct), times(1));
        }
    }


    @Test
    void testUpdateStoreProductNotFound() {
        StoreProductDto storeProductDto = new StoreProductDto();
        storeProductDto.setId("1");

        when(storeProductRepository.findById(storeProductDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeProductService.updateStoreProduct(storeProductDto));
        verify(storeProductRepository, times(1)).findById(storeProductDto.getId());
    }

    @Test
    void testDeleteStoreProduct() {
        String id = "1";
        StoreProduct storeProduct = new StoreProduct();
        storeProduct.setId(id);

        when(storeProductRepository.findById(id)).thenReturn(Optional.of(storeProduct));
        doNothing().when(storeProductRepository).deleteById(id);

        boolean result = storeProductService.deleteStoreProduct(id);

        assertTrue(result);
        verify(storeProductRepository, times(1)).findById(id);
        verify(storeProductRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteStoreProductNotFound() {
        String id = "1";

        when(storeProductRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> storeProductService.deleteStoreProduct(id));
        verify(storeProductRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllStoreProducts() {
        List<StoreProduct> storeProductList = new ArrayList<>();
        storeProductList.add(new StoreProduct());
        storeProductList.add(new StoreProduct());

        when(storeProductRepository.findAll()).thenReturn(storeProductList);

        List<StoreProduct> result = storeProductService.fetchAllStoreProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(storeProductRepository, times(1)).findAll();
    }



}
