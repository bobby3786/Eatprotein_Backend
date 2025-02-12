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

import com.catalog.dto.CouponsUtilDto;
import com.catalog.entity.CouponsUtil;
import com.catalog.repository.CouponsUtilRepository;
import com.catalog.service.impl.CouponsUtilServiceImpl;

import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;


import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class CouponsUtilServiceImplTest {
	
	
		@Mock
	    private CouponsUtilRepository couponsUtilRepository;

	    @InjectMocks
	    private CouponsUtilServiceImpl couponsUtilService;
	    

	    @Test
	    void testCreateCouponsUtil() {
	    	
	    	 CouponsUtilDto couponsUtilDto = new CouponsUtilDto();
	    	 couponsUtilDto.setExpireDays(10);
			  couponsUtilDto.setMaxLimit(100);
			  couponsUtilDto.setReferralPercentage(40);
			  couponsUtilDto.setReferredPercentage(30);
	        
	        
	        CouponsUtil CouponsUtil = new CouponsUtil();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(couponsUtilDto, CouponsUtil))
	                        .thenAnswer(invocation -> null);

	            when(couponsUtilRepository.save(any(CouponsUtil.class))).thenReturn(CouponsUtil);

	            couponsUtilService.createCouponsUtil(couponsUtilDto);

	            verify(couponsUtilRepository, times(1)).save(any(CouponsUtil.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(couponsUtilDto, CouponsUtil), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateCouponsUtil_NullDto() {
	       
	        CouponsUtilDto CouponsUtilDto = null;

	        assertThrows(IllegalArgumentException.class, () -> couponsUtilService.createCouponsUtil(CouponsUtilDto));
	        verify(couponsUtilRepository, never()).save(any(CouponsUtil.class));
	    }
	    
	    @Test
	    void testFetchCouponsUtil() {
	     
	        int id = 1; 

	        CouponsUtil couponsUtil = new CouponsUtil();
	        couponsUtil.setId(id);
	        couponsUtil.setExpireDays(10);
	        couponsUtil.setMaxLimit(100);
	        couponsUtil.setReferralPercentage(40);
	        couponsUtil.setReferredPercentage(30);


	       when(couponsUtilRepository.findById(id)).thenReturn(Optional.of(couponsUtil));

	        CouponsUtil result = couponsUtilService.fetchCouponsUtil(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals(10, result.getExpireDays());
	        assertEquals(100, result.getMaxLimit());
	        assertEquals(40, result.getReferralPercentage());
	        assertEquals(30, result.getReferredPercentage());
	        verify(couponsUtilRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchCouponsUtilNotFound() {
	        int id = 1;

	        when(couponsUtilRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> couponsUtilService.fetchCouponsUtil(id));
	        verify(couponsUtilRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateCouponsUtil() {
	        
	    	CouponsUtilDto couponsUtilDto = new CouponsUtilDto();
	    	couponsUtilDto.setId(1);
	    	couponsUtilDto.setExpireDays(10);
			couponsUtilDto.setMaxLimit(100);
		    couponsUtilDto.setReferralPercentage(40);
		    couponsUtilDto.setReferredPercentage(30);

	        CouponsUtil existingCouponsUtil = new CouponsUtil();
	        existingCouponsUtil.setId(1); 
	        existingCouponsUtil.setExpireDays(10);
	        existingCouponsUtil.setMaxLimit(100);
	        existingCouponsUtil.setReferralPercentage(40);
	        existingCouponsUtil.setReferredPercentage(30);

	        when(couponsUtilRepository.findById(couponsUtilDto.getId())).thenReturn(Optional.of(existingCouponsUtil));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(couponsUtilDto, existingCouponsUtil))
	                        .thenAnswer(invocation -> null);

	            when(couponsUtilRepository.save(existingCouponsUtil)).thenReturn(existingCouponsUtil);

	            boolean result = couponsUtilService.updateCouponsUtil(couponsUtilDto);

	            assertTrue(result);
	            verify(couponsUtilRepository, times(1)).findById(couponsUtilDto.getId());
	            verify(couponsUtilRepository, times(1)).save(existingCouponsUtil);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(couponsUtilDto, existingCouponsUtil), times(1));
	        }
	    }


	    @Test
	    void testUpdateCouponsUtilNotFound() {
	        CouponsUtilDto couponsUtilDto = new CouponsUtilDto();
	        couponsUtilDto.setId(1);

	        when(couponsUtilRepository.findById(couponsUtilDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> couponsUtilService.updateCouponsUtil(couponsUtilDto));
	        verify(couponsUtilRepository, times(1)).findById(couponsUtilDto.getId());
	    }

	    @Test
	    void testDeleteCouponsUtil() {
	        int id = 1;
	        CouponsUtil couponsUtil = new CouponsUtil();
	        couponsUtil.setId(id);

	        when(couponsUtilRepository.findById(id)).thenReturn(Optional.of(couponsUtil));
	        doNothing().when(couponsUtilRepository).deleteById(id);

	        boolean result = couponsUtilService.deleteCouponsUtil(id);

	        assertTrue(result);
	        verify(couponsUtilRepository, times(1)).findById(id);
	        verify(couponsUtilRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteCouponsUtilNotFound() {
	        int id = 1;

	        when(couponsUtilRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> couponsUtilService.deleteCouponsUtil(id));
	        verify(couponsUtilRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllCouponsUtils() {
	        List<CouponsUtil> couponsUtilList = new ArrayList<>();
	        couponsUtilList.add(new CouponsUtil());
	        couponsUtilList.add(new CouponsUtil());

	        when(couponsUtilRepository.findAll()).thenReturn(couponsUtilList);

	        List<CouponsUtil> result = couponsUtilService.fetchAllCouponsUtils();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(couponsUtilRepository, times(1)).findAll();
	    }


	}


