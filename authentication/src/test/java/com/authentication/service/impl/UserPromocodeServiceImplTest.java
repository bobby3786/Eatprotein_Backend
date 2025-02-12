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

import com.authentication.dto.UserPromocodeDto;
import com.authentication.entity.UserPromocode;
import com.authentication.repository.UserPromocodeRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class UserPromocodeServiceImplTest {
	
	 @Mock
	    private UserPromocodeRepository userPromocodeRepository;

	    @InjectMocks
	    private UserPromocodeServiceImpl userPromocodeService;
	    

	    @Test
	    void testCreateUserPromocode() {
	    	
	    	 UserPromocodeDto userPromocodeDto = new UserPromocodeDto();
	    	 userPromocodeDto.setUserPhone("9182291822");
			  userPromocodeDto.setUserId(1);
	        
	        
	        UserPromocode userPromocode = new UserPromocode();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userPromocodeDto, userPromocode))
	                        .thenAnswer(invocation -> null);

	            when(userPromocodeRepository.save(any(UserPromocode.class))).thenReturn(userPromocode);

	            userPromocodeService.createUserPromocode(userPromocodeDto);

	            verify(userPromocodeRepository, times(1)).save(any(UserPromocode.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userPromocodeDto, userPromocode), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateUserPromocode_NullDto() {
	       
	        UserPromocodeDto userPromocodeDto = null;

	        assertThrows(IllegalArgumentException.class, () -> userPromocodeService.createUserPromocode(userPromocodeDto));
	        verify(userPromocodeRepository, never()).save(any(UserPromocode.class));
	    }
	    
	    @Test
	    void testFetchUserPromocode() {
	     
	        int id = 1; 

	        UserPromocode userPromocode = new UserPromocode();
	        userPromocode.setId(id);
	        userPromocode.setUserPhone("9182291822");
	        userPromocode.setUserId(1);


	       when(userPromocodeRepository.findById(id)).thenReturn(Optional.of(userPromocode));

	        UserPromocode result = userPromocodeService.fetchUserPromocode(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("9182291822", result.getUserPhone()); 
	        assertEquals(1, result.getUserId()); 
	        verify(userPromocodeRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchUserPromocodeNotFound() {
	        int id = 1;

	        when(userPromocodeRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> userPromocodeService.fetchUserPromocode(id));
	        verify(userPromocodeRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateUserPromocode() {
	        
	    	UserPromocodeDto userPromocodeDto = new UserPromocodeDto();
	    	userPromocodeDto.setId(1);
	    	 userPromocodeDto.setUserPhone("9182291822");
			  userPromocodeDto.setUserId(1);

	        UserPromocode existingUserPromocode = new UserPromocode();
	        existingUserPromocode.setId(1); 
	        existingUserPromocode.setUserPhone("9182291822");
	        existingUserPromocode.setUserId(1);

	        when(userPromocodeRepository.findById(userPromocodeDto.getId())).thenReturn(Optional.of(existingUserPromocode));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userPromocodeDto, existingUserPromocode))
	                        .thenAnswer(invocation -> null);

	            when(userPromocodeRepository.save(existingUserPromocode)).thenReturn(existingUserPromocode);

	            boolean result = userPromocodeService.updateUserPromocode(userPromocodeDto);

	            assertTrue(result);
	            verify(userPromocodeRepository, times(1)).findById(userPromocodeDto.getId());
	            verify(userPromocodeRepository, times(1)).save(existingUserPromocode);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userPromocodeDto, existingUserPromocode), times(1));
	        }
	    }


	    @Test
	    void testUpdateUserPromocodeNotFound() {
	        UserPromocodeDto userPromocodeDto = new UserPromocodeDto();
	        userPromocodeDto.setId(1);

	        when(userPromocodeRepository.findById(userPromocodeDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> userPromocodeService.updateUserPromocode(userPromocodeDto));
	        verify(userPromocodeRepository, times(1)).findById(userPromocodeDto.getId());
	    }

	    @Test
	    void testDeleteUserPromocode() {
	        int id = 1;
	        UserPromocode userPromocode = new UserPromocode();
	        userPromocode.setId(id);

	        when(userPromocodeRepository.findById(id)).thenReturn(Optional.of(userPromocode));
	        doNothing().when(userPromocodeRepository).deleteById(id);

	        boolean result = userPromocodeService.deleteUserPromocode(id);

	        assertTrue(result);
	        verify(userPromocodeRepository, times(1)).findById(id);
	        verify(userPromocodeRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteUserPromocodeNotFound() {
	        int id = 1;

	        when(userPromocodeRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> userPromocodeService.deleteUserPromocode(id));
	        verify(userPromocodeRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllUserPromocodes() {
	        List<UserPromocode> userPromocodeList = new ArrayList<>();
	        userPromocodeList.add(new UserPromocode());
	        userPromocodeList.add(new UserPromocode());

	        when(userPromocodeRepository.findAll()).thenReturn(userPromocodeList);

	        List<UserPromocode> result = userPromocodeService.fetchAllUserPromocodes();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(userPromocodeRepository, times(1)).findAll();
	    }


}
