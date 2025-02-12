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

import com.authentication.dto.UserAddressDto;
import com.authentication.entity.UserAddress;
import com.authentication.repository.UserAddressRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class UserAddressServiceImplTest {
	
	@Mock
    private UserAddressRepository userAddressRepository;

    @InjectMocks
    private UserAddressServiceImpl userAddressService;
    

    @Test
    void testCreateUserAddress() {
    	
    	 UserAddressDto userAddressDto = new UserAddressDto();
    	 userAddressDto.setName("Bobby");
		  userAddressDto.setCity("kavali");
        
        
        UserAddress userAddress = new UserAddress();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userAddressDto, userAddress))
                        .thenAnswer(invocation -> null);

            when(userAddressRepository.save(any(UserAddress.class))).thenReturn(userAddress);

            userAddressService.createUserAddress(userAddressDto);

            verify(userAddressRepository, times(1)).save(any(UserAddress.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userAddressDto, userAddress), times(1));
        }
    }
    
    @Test
    void testCreateUserAddress_NullDto() {
       
        UserAddressDto userAddressDto = null;

        assertThrows(IllegalArgumentException.class, () -> userAddressService.createUserAddress(userAddressDto));
        verify(userAddressRepository, never()).save(any(UserAddress.class));
    }
    
    @Test
    void testFetchUserAddress() {
     
        int id = 1; 

        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        userAddress.setName("Bobby");
        userAddress.setCity("kavali");


       when(userAddressRepository.findById(id)).thenReturn(Optional.of(userAddress));

        UserAddress result = userAddressService.fetchUserAddress(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("Bobby", result.getName()); 
        assertEquals("kavali", result.getCity()); 
        verify(userAddressRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUserAddressNotFound() {
        int id = 1;

        when(userAddressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAddressService.fetchUserAddress(id));
        verify(userAddressRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserAddress() {
        
    	UserAddressDto userAddressDto = new UserAddressDto();
    	userAddressDto.setId(1);
    	userAddressDto.setName("Bobby");
		  userAddressDto.setCity("kavali");

        UserAddress existingUserAddress = new UserAddress();
        existingUserAddress.setId(1); 
        existingUserAddress.setName("Bobby");
        existingUserAddress.setCity("kavali");

        when(userAddressRepository.findById(userAddressDto.getId())).thenReturn(Optional.of(existingUserAddress));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userAddressDto, existingUserAddress))
                        .thenAnswer(invocation -> null);

            when(userAddressRepository.save(existingUserAddress)).thenReturn(existingUserAddress);

            boolean result = userAddressService.updateUserAddress(userAddressDto);

            assertTrue(result);
            verify(userAddressRepository, times(1)).findById(userAddressDto.getId());
            verify(userAddressRepository, times(1)).save(existingUserAddress);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userAddressDto, existingUserAddress), times(1));
        }
    }


    @Test
    void testUpdateUserAddressNotFound() {
        UserAddressDto userAddressDto = new UserAddressDto();
        userAddressDto.setId(1);

        when(userAddressRepository.findById(userAddressDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAddressService.updateUserAddress(userAddressDto));
        verify(userAddressRepository, times(1)).findById(userAddressDto.getId());
    }

    @Test
    void testDeleteUserAddress() {
        int id = 1;
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);

        when(userAddressRepository.findById(id)).thenReturn(Optional.of(userAddress));
        doNothing().when(userAddressRepository).deleteById(id);

        boolean result = userAddressService.deleteUserAddress(id);

        assertTrue(result);
        verify(userAddressRepository, times(1)).findById(id);
        verify(userAddressRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserAddressNotFound() {
        int id = 1;

        when(userAddressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAddressService.deleteUserAddress(id));
        verify(userAddressRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUserAddresss() {
        List<UserAddress> userAddressList = new ArrayList<>();
        userAddressList.add(new UserAddress());
        userAddressList.add(new UserAddress());

        when(userAddressRepository.findAll()).thenReturn(userAddressList);

        List<UserAddress> result = userAddressService.fetchAllUserAddresses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userAddressRepository, times(1)).findAll();
    }


}
