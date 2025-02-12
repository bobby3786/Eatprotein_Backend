package com.apputil.service.impl;

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

import com.apputil.dto.BannerStoreDto;
import com.apputil.entity.BannerStore;
import com.apputil.repository.BannerStoreRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class BannerStoreServiceImplTest {
	
	  @Mock
	    private BannerStoreRepository bannerStoreRepository;

	    @InjectMocks
	    private BannerStoreServiceImpl bannerStoreService;
	    

	    @Test
	    void testCreateBannerStore() {
	    	
	    	 BannerStoreDto bannerStoreDto = new BannerStoreDto();
	    	 bannerStoreDto.setImagePath("apputil/BannerStore.jpg");
	    	 bannerStoreDto.setStatus("Active");
	        
	        
	        BannerStore bannerStore = new BannerStore();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerStoreDto, bannerStore))
	                        .thenAnswer(invocation -> null);

	            when(bannerStoreRepository.save(any(BannerStore.class))).thenReturn(bannerStore);

	            bannerStoreService.createBannerStore(bannerStoreDto);

	            verify(bannerStoreRepository, times(1)).save(any(BannerStore.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerStoreDto, bannerStore), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateBannerStore_NullDto() {
	       
	        BannerStoreDto bannerStoreDto = null;

	        assertThrows(IllegalArgumentException.class, () -> bannerStoreService.createBannerStore(bannerStoreDto));
	        verify(bannerStoreRepository, never()).save(any(BannerStore.class));
	    }
	    
	    @Test
	    void testFetchBannerStore() {
	     
	        int id = 1; 

	        BannerStore bannerStore = new BannerStore();
	        bannerStore.setId(id);
	        bannerStore.setImagePath("apputil/BannerStore.jpg");
	        bannerStore.setStatus("Active"); 


	       when(bannerStoreRepository.findById(id)).thenReturn(Optional.of(bannerStore));

	        BannerStore result = bannerStoreService.fetchBannerStore(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("apputil/BannerStore.jpg", result.getImagePath()); 
	        assertEquals("Active", result.getStatus()); 
	        verify(bannerStoreRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchBannerStoreNotFound() {
	        int id = 1;

	        when(bannerStoreRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> bannerStoreService.fetchBannerStore(id));
	        verify(bannerStoreRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateBannerStore() {
	        
	    	BannerStoreDto bannerStoreDto = new BannerStoreDto();
	    	bannerStoreDto.setId(1);
	    	bannerStoreDto.setStatus("Active");

	        BannerStore existingBannerStore = new BannerStore();
	        existingBannerStore.setId(1); 
	        existingBannerStore.setStatus("InActive"); 

	        when(bannerStoreRepository.findById(bannerStoreDto.getId())).thenReturn(Optional.of(existingBannerStore));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerStoreDto, existingBannerStore))
	                        .thenAnswer(invocation -> null);

	            when(bannerStoreRepository.save(existingBannerStore)).thenReturn(existingBannerStore);

	            boolean result = bannerStoreService.updateBannerStore(bannerStoreDto);

	            assertTrue(result);
	            verify(bannerStoreRepository, times(1)).findById(bannerStoreDto.getId());
	            verify(bannerStoreRepository, times(1)).save(existingBannerStore);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerStoreDto, existingBannerStore), times(1));
	        }
	    }


	    @Test
	    void testUpdateBannerStoreNotFound() {
	        BannerStoreDto bannerStoreDto = new BannerStoreDto();
	        bannerStoreDto.setId(1);

	        when(bannerStoreRepository.findById(bannerStoreDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> bannerStoreService.updateBannerStore(bannerStoreDto));
	        verify(bannerStoreRepository, times(1)).findById(bannerStoreDto.getId());
	    }

	    @Test
	    void testDeleteBannerStore() {
	        int id = 1;
	        BannerStore bannerStore = new BannerStore();
	        bannerStore.setId(id);

	        when(bannerStoreRepository.findById(id)).thenReturn(Optional.of(bannerStore));
	        doNothing().when(bannerStoreRepository).deleteById(id);

	        boolean result = bannerStoreService.deleteBannerStore(id);

	        assertTrue(result);
	        verify(bannerStoreRepository, times(1)).findById(id);
	        verify(bannerStoreRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteBannerStoreNotFound() {
	        int id = 1;

	        when(bannerStoreRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> bannerStoreService.deleteBannerStore(id));
	        verify(bannerStoreRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllBannerStores() {
	        List<BannerStore> bannerStoreList = new ArrayList<>();
	        bannerStoreList.add(new BannerStore());
	        bannerStoreList.add(new BannerStore());

	        when(bannerStoreRepository.findAll()).thenReturn(bannerStoreList);

	        List<BannerStore> result = bannerStoreService.fetchAllBannerStores();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(bannerStoreRepository, times(1)).findAll();
	    }

}

