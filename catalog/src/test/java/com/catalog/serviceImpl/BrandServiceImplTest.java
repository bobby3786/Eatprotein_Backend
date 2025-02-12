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

import com.catalog.dto.BrandDto;
import com.catalog.entity.Brand;
import com.catalog.repository.BrandRepository;
import com.catalog.service.impl.BrandServiceImpl;

import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;


import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {
	
	  @Mock
	    private BrandRepository brandRepository;

	    @InjectMocks
	    private BrandServiceImpl brandService;
	    

	    @Test
	    void testCreateBrand() {
	    	
	    	 BrandDto brandDto = new BrandDto();
	    	 brandDto.setStatus("Active");
	        
	        
	        Brand brand = new Brand();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(brandDto, brand))
	                        .thenAnswer(invocation -> null);

	            when(brandRepository.save(any(Brand.class))).thenReturn(brand);

	            brandService.createBrand(brandDto);

	            verify(brandRepository, times(1)).save(any(Brand.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(brandDto, brand), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateBrand_NullDto() {
	       
	        BrandDto brandDto = null;

	        assertThrows(IllegalArgumentException.class, () -> brandService.createBrand(brandDto));
	        verify(brandRepository, never()).save(any(Brand.class));
	    }
	    
	    @Test
	    void testFetchBrand() {
	     
	        int id = 1; 

	        Brand brand = new Brand();
	        brand.setId(id);
	        brand.setStatus("Active"); 


	       when(brandRepository.findById(id)).thenReturn(Optional.of(brand));

	        Brand result = brandService.fetchBrand(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("Active", result.getStatus()); 
	        verify(brandRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchBrandNotFound() {
	        int id = 1;

	        when(brandRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> brandService.fetchBrand(id));
	        verify(brandRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateBrand() {
	        
	    	BrandDto brandDto = new BrandDto();
	    	brandDto.setId(1);
	    	brandDto.setStatus("Active");

	        Brand existingBrand = new Brand();
	        existingBrand.setId(1); 
	        existingBrand.setStatus("InActive"); 

	        when(brandRepository.findById(brandDto.getId())).thenReturn(Optional.of(existingBrand));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(brandDto, existingBrand))
	                        .thenAnswer(invocation -> null);

	            when(brandRepository.save(existingBrand)).thenReturn(existingBrand);

	            boolean result = brandService.updateBrand(brandDto);

	            assertTrue(result);
	            verify(brandRepository, times(1)).findById(brandDto.getId());
	            verify(brandRepository, times(1)).save(existingBrand);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(brandDto, existingBrand), times(1));
	        }
	    }


	    @Test
	    void testUpdateBrandNotFound() {
	        BrandDto brandDto = new BrandDto();
	        brandDto.setId(1);

	        when(brandRepository.findById(brandDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> brandService.updateBrand(brandDto));
	        verify(brandRepository, times(1)).findById(brandDto.getId());
	    }

	    @Test
	    void testDeleteBrand() {
	        int id = 1;
	        Brand brand = new Brand();
	        brand.setId(id);

	        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));
	        doNothing().when(brandRepository).deleteById(id);

	        boolean result = brandService.deleteBrand(id);

	        assertTrue(result);
	        verify(brandRepository, times(1)).findById(id);
	        verify(brandRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteBrandNotFound() {
	        int id = 1;

	        when(brandRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> brandService.deleteBrand(id));
	        verify(brandRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllBrands() {
	        List<Brand> brandList = new ArrayList<>();
	        brandList.add(new Brand());
	        brandList.add(new Brand());

	        when(brandRepository.findAll()).thenReturn(brandList);

	        List<Brand> result = brandService.fetchAllBrands();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(brandRepository, times(1)).findAll();
	    }

}

