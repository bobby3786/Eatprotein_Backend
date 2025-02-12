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

import com.apputil.dto.AVersionDto;
import com.apputil.entity.AppVersion;
import com.apputil.repository.AVersionRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class AppVersionServiceImplTest {
	
	 @Mock
	    private AVersionRepository appVersionRepository;

	    @InjectMocks
	    private AppVersionServiceImpl appVersionService;
	    

	    @Test
	    void testCreateAppVersion() {
	    	
	    	 AVersionDto appVersionDto = new AVersionDto();
			  appVersionDto.setAndroidEmployeeVersion("1.0.3");
			  appVersionDto.setAndroidVersion("1.0.2");
			  appVersionDto.setIosEmployeeVersion("2.0.3");
			  appVersionDto.setIosVersion("2.0.4");
	        
	        AppVersion appVersion = new AppVersion();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(appVersionDto, appVersion))
	                        .thenAnswer(invocation -> null);

	            when(appVersionRepository.save(any(AppVersion.class))).thenReturn(appVersion);

	            appVersionService.createAppVersion(appVersionDto);

	            verify(appVersionRepository, times(1)).save(any(AppVersion.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(appVersionDto, appVersion), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateAppVersion_NullDto() {
	       
	    	AVersionDto appVersionDto = null;

	        assertThrows(IllegalArgumentException.class, () -> appVersionService.createAppVersion(appVersionDto));
	        verify(appVersionRepository, never()).save(any(AppVersion.class));
	    }
	    
	    @Test
	    void testFetchAppVersion() {
	     
	        int id = 1; 

	        AppVersion appVersion = new AppVersion();
			  appVersion.setId(id);
			  appVersion.setAndroidEmployeeVersion("1.0.3");

	       when(appVersionRepository.findById(id)).thenReturn(Optional.of(appVersion));

	       AppVersion result = appVersionService.fetchAppVersion(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("1.0.3", result.getAndroidEmployeeVersion()); 
	        verify(appVersionRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchAppVersionNotFound() {
	        int id = 1;

	        when(appVersionRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> appVersionService.fetchAppVersion(id));
	        verify(appVersionRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateAppVersion() {
	        
	    	AVersionDto appVersionDto = new AVersionDto();
	    	 appVersionDto.setId(1);
			  appVersionDto.setAndroidEmployeeVersion("1.0.3");

			  AppVersion existingAppVersion = new AppVersion();
			  existingAppVersion.setId(1); 
			  existingAppVersion.setAndroidEmployeeVersion("1.0.3"); 

	        when(appVersionRepository.findById(appVersionDto.getId())).thenReturn(Optional.of(existingAppVersion));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(appVersionDto, existingAppVersion))
	                        .thenAnswer(invocation -> null);

	            when(appVersionRepository.save(existingAppVersion)).thenReturn(existingAppVersion);

	            boolean result = appVersionService.updateAppVersion(appVersionDto);

	            assertTrue(result);
	            verify(appVersionRepository, times(1)).findById(appVersionDto.getId());
	            verify(appVersionRepository, times(1)).save(existingAppVersion);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(appVersionDto, existingAppVersion), times(1));
	        }
	    }


	    @Test
	    void testUpdateAppVersionNotFound() {
	    	
	    	AVersionDto appVersionDto = new AVersionDto();
	    	 appVersionDto.setId(1);

	        when(appVersionRepository.findById(appVersionDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> appVersionService.updateAppVersion(appVersionDto));
	        verify(appVersionRepository, times(1)).findById(appVersionDto.getId());
	    }

	    @Test
	    void testDeleteAppVersion() {
	        int id = 1;
	        AppVersion appVersion = new AppVersion();
			  appVersion.setId(id);

	        when(appVersionRepository.findById(id)).thenReturn(Optional.of(appVersion));
	        doNothing().when(appVersionRepository).deleteById(id);

	        boolean result = appVersionService.deleteAppVersion(id);

	        assertTrue(result);
	        verify(appVersionRepository, times(1)).findById(id);
	        verify(appVersionRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteAppVersionNotFound() {
	        int id = 1;

	        when(appVersionRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> appVersionService.deleteAppVersion(id));
	        verify(appVersionRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllAppVersion() {
	        List<AppVersion> appVersionList = new ArrayList<>();
	        appVersionList.add(new AppVersion());
	        appVersionList.add(new AppVersion());

	        when(appVersionRepository.findAll()).thenReturn(appVersionList);

	        List<AppVersion> result = appVersionService.fetchAllAppVersions();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(appVersionRepository, times(1)).findAll();
	    }

}
