package com.authentication.service.impl;

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

import com.authentication.dto.FcmAuthTokenDto;
import com.authentication.entity.FcmAuthToken;
import com.authentication.repository.FcmAuthTokenRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class FcmAuthTokenServiceImplTest {
	
	    @Mock
	    private FcmAuthTokenRepository fcmAuthTokenRepository;

	    @InjectMocks
	    private FcmAuthTokenServiceImpl fcmAuthTokenService;
	    

	    @Test
	    void testCreateFcmAuthToken() {
	    	
	    	 FcmAuthTokenDto fcmAuthTokenDto = new FcmAuthTokenDto();
	    	 fcmAuthTokenDto.setToken("abcd");
			  fcmAuthTokenDto.setExpiresIn(5);
	        
	        
	        FcmAuthToken fcmAuthToken = new FcmAuthToken();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(fcmAuthTokenDto, fcmAuthToken))
	                        .thenAnswer(invocation -> null);

	            when(fcmAuthTokenRepository.save(any(FcmAuthToken.class))).thenReturn(fcmAuthToken);

	            fcmAuthTokenService.createFcmAuthToken(fcmAuthTokenDto);

	            verify(fcmAuthTokenRepository, times(1)).save(any(FcmAuthToken.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(fcmAuthTokenDto, fcmAuthToken), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateFcmAuthToken_NullDto() {
	       
	        FcmAuthTokenDto fcmAuthTokenDto = null;

	        assertThrows(IllegalArgumentException.class, () -> fcmAuthTokenService.createFcmAuthToken(fcmAuthTokenDto));
	        verify(fcmAuthTokenRepository, never()).save(any(FcmAuthToken.class));
	    }
	    
	    @Test
	    void testFetchFcmAuthToken() {
	     
	        int id = 1; 

	        FcmAuthToken fcmAuthToken = new FcmAuthToken();
	        fcmAuthToken.setId(id);
	        fcmAuthToken.setToken("abcd");
	        fcmAuthToken.setExpiresIn(5);


	       when(fcmAuthTokenRepository.findById(id)).thenReturn(Optional.of(fcmAuthToken));

	        FcmAuthToken result = fcmAuthTokenService.fetchFcmAuthToken(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("abcd", result.getToken()); 
	        assertEquals(5, result.getExpiresIn()); 
	        verify(fcmAuthTokenRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchFcmAuthTokenNotFound() {
	        int id = 1;

	        when(fcmAuthTokenRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> fcmAuthTokenService.fetchFcmAuthToken(id));
	        verify(fcmAuthTokenRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateFcmAuthToken() {
	        
	    	FcmAuthTokenDto fcmAuthTokenDto = new FcmAuthTokenDto();
	    	fcmAuthTokenDto.setId(1);
	    	fcmAuthTokenDto.setToken("abcd");
			  fcmAuthTokenDto.setExpiresIn(5);

	        FcmAuthToken existingFcmAuthToken = new FcmAuthToken();
	        existingFcmAuthToken.setId(1); 
	        existingFcmAuthToken.setToken("abcd");
	        existingFcmAuthToken.setExpiresIn(5);

	        when(fcmAuthTokenRepository.findById(fcmAuthTokenDto.getId())).thenReturn(Optional.of(existingFcmAuthToken));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(fcmAuthTokenDto, existingFcmAuthToken))
	                        .thenAnswer(invocation -> null);

	            when(fcmAuthTokenRepository.save(existingFcmAuthToken)).thenReturn(existingFcmAuthToken);

	            boolean result = fcmAuthTokenService.updateFcmAuthToken(fcmAuthTokenDto);

	            assertTrue(result);
	            verify(fcmAuthTokenRepository, times(1)).findById(fcmAuthTokenDto.getId());
	            verify(fcmAuthTokenRepository, times(1)).save(existingFcmAuthToken);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(fcmAuthTokenDto, existingFcmAuthToken), times(1));
	        }
	    }


	    @Test
	    void testUpdateFcmAuthTokenNotFound() {
	        FcmAuthTokenDto fcmAuthTokenDto = new FcmAuthTokenDto();
	        fcmAuthTokenDto.setId(1);

	        when(fcmAuthTokenRepository.findById(fcmAuthTokenDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> fcmAuthTokenService.updateFcmAuthToken(fcmAuthTokenDto));
	        verify(fcmAuthTokenRepository, times(1)).findById(fcmAuthTokenDto.getId());
	    }

	    @Test
	    void testDeleteFcmAuthToken() {
	        int id = 1;
	        FcmAuthToken fcmAuthToken = new FcmAuthToken();
	        fcmAuthToken.setId(id);

	        when(fcmAuthTokenRepository.findById(id)).thenReturn(Optional.of(fcmAuthToken));
	        doNothing().when(fcmAuthTokenRepository).deleteById(id);

	        boolean result = fcmAuthTokenService.deleteFcmAuthToken(id);

	        assertTrue(result);
	        verify(fcmAuthTokenRepository, times(1)).findById(id);
	        verify(fcmAuthTokenRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteFcmAuthTokenNotFound() {
	        int id = 1;

	        when(fcmAuthTokenRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> fcmAuthTokenService.deleteFcmAuthToken(id));
	        verify(fcmAuthTokenRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllFcmAuthTokens() {
	        List<FcmAuthToken> fcmAuthTokenList = new ArrayList<>();
	        fcmAuthTokenList.add(new FcmAuthToken());
	        fcmAuthTokenList.add(new FcmAuthToken());

	        when(fcmAuthTokenRepository.findAll()).thenReturn(fcmAuthTokenList);

	        List<FcmAuthToken> result = fcmAuthTokenService.fetchAllFcmAuthTokens();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(fcmAuthTokenRepository, times(1)).findAll();
	    }


}
