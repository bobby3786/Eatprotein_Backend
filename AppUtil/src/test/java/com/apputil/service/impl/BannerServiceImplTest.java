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

import com.apputil.dto.BannerDto;
import com.apputil.entity.Banner;
import com.apputil.repository.BannerRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class BannerServiceImplTest {
	
	  @Mock
	    private BannerRepository bannerRepository;

	    @InjectMocks
	    private BannerServiceImpl bannerService;
	    

	    @Test
	    void testCreateBanner() {
	    	
	    	 BannerDto bannerDto = new BannerDto();
			  bannerDto.setImagePath("apputil/banner.jpg");
			  bannerDto.setStatus("Active");
	        
	        
	        Banner banner = new Banner();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerDto, banner))
	                        .thenAnswer(invocation -> null);

	            when(bannerRepository.save(any(Banner.class))).thenReturn(banner);

	            bannerService.createBanner(bannerDto);

	            verify(bannerRepository, times(1)).save(any(Banner.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerDto, banner), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateBanner_NullDto() {
	       
	        BannerDto bannerDto = null;

	        assertThrows(IllegalArgumentException.class, () -> bannerService.createBanner(bannerDto));
	        verify(bannerRepository, never()).save(any(Banner.class));
	    }
	    
	    @Test
	    void testFetchBanner() {
	     
	        int id = 1; 

	        Banner banner = new Banner();
		      banner.setId(id);
		      banner.setImagePath("apputil/banner.jpg");
		      banner.setStatus("Active"); 


	       when(bannerRepository.findById(id)).thenReturn(Optional.of(banner));

	        Banner result = bannerService.fetchBanner(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("apputil/banner.jpg", result.getImagePath()); 
	        assertEquals("Active", result.getStatus()); 
	        verify(bannerRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchBannerNotFound() {
	        int id = 1;

	        when(bannerRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> bannerService.fetchBanner(id));
	        verify(bannerRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateBanner() {
	        
	    	BannerDto bannerDto = new BannerDto();
		      bannerDto.setId(1);
		      bannerDto.setStatus("Active");

	        Banner existingBanner = new Banner();
	        existingBanner.setId(1); 
	        existingBanner.setStatus("InActive"); 

	        when(bannerRepository.findById(bannerDto.getId())).thenReturn(Optional.of(existingBanner));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerDto, existingBanner))
	                        .thenAnswer(invocation -> null);

	            when(bannerRepository.save(existingBanner)).thenReturn(existingBanner);

	            boolean result = bannerService.updateBanner(bannerDto);

	            assertTrue(result);
	            verify(bannerRepository, times(1)).findById(bannerDto.getId());
	            verify(bannerRepository, times(1)).save(existingBanner);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(bannerDto, existingBanner), times(1));
	        }
	    }


	    @Test
	    void testUpdateBannerNotFound() {
	        BannerDto BannerDto = new BannerDto();
	        BannerDto.setId(1);

	        when(bannerRepository.findById(BannerDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> bannerService.updateBanner(BannerDto));
	        verify(bannerRepository, times(1)).findById(BannerDto.getId());
	    }

	    @Test
	    void testDeleteBanner() {
	        int id = 1;
	        Banner banner = new Banner();
	        banner.setId(id);

	        when(bannerRepository.findById(id)).thenReturn(Optional.of(banner));
	        doNothing().when(bannerRepository).deleteById(id);

	        boolean result = bannerService.deleteBanner(id);

	        assertTrue(result);
	        verify(bannerRepository, times(1)).findById(id);
	        verify(bannerRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteBannerNotFound() {
	        int id = 1;

	        when(bannerRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> bannerService.deleteBanner(id));
	        verify(bannerRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllBanners() {
	        List<Banner> bannerList = new ArrayList<>();
	        bannerList.add(new Banner());
	        bannerList.add(new Banner());

	        when(bannerRepository.findAll()).thenReturn(bannerList);

	        List<Banner> result = bannerService.fetchAllBanners();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(bannerRepository, times(1)).findAll();
	    }

}
