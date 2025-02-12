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

import com.authentication.dto.UserWalletDto;
import com.authentication.entity.UserWallet;
import com.authentication.repository.UserWalletRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class UserWalletServiceImplTest {
	
	@Mock
    private UserWalletRepository userWalletRepository;

    @InjectMocks
    private UserWalletServiceImpl userWalletService;
    

    @Test
    void testCreateUserWallet() {
    	
    	 UserWalletDto userWalletDto = new UserWalletDto();
    	 userWalletDto.setUserId(2);
		  userWalletDto.setAvailableTotal("500");
        
        
        UserWallet userWallet = new UserWallet();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userWalletDto, userWallet))
                        .thenAnswer(invocation -> null);

            when(userWalletRepository.save(any(UserWallet.class))).thenReturn(userWallet);

            userWalletService.createUserWallet(userWalletDto);

            verify(userWalletRepository, times(1)).save(any(UserWallet.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userWalletDto, userWallet), times(1));
        }
    }
    
    @Test
    void testCreateUserWallet_NullDto() {
       
        UserWalletDto userWalletDto = null;

        assertThrows(IllegalArgumentException.class, () -> userWalletService.createUserWallet(userWalletDto));
        verify(userWalletRepository, never()).save(any(UserWallet.class));
    }
    
    @Test
    void testFetchUserWallet() {
     
        int id = 1; 

        UserWallet userWallet = new UserWallet();
        userWallet.setId(id);
        userWallet.setUserId(2);
        userWallet.setAvailableTotal("500");


       when(userWalletRepository.findById(id)).thenReturn(Optional.of(userWallet));

        UserWallet result = userWalletService.fetchUserWallet(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(2, result.getUserId()); 
        assertEquals("500", result.getAvailableTotal()); 
        verify(userWalletRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUserWalletNotFound() {
        int id = 1;

        when(userWalletRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userWalletService.fetchUserWallet(id));
        verify(userWalletRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserWallet() {
        
    	UserWalletDto userWalletDto = new UserWalletDto();
    	userWalletDto.setId(1);
    	userWalletDto.setUserId(2);
		  userWalletDto.setAvailableTotal("500");

        UserWallet existingUserWallet = new UserWallet();
        existingUserWallet.setId(1); 
        existingUserWallet.setUserId(2);
        existingUserWallet.setAvailableTotal("500");

        when(userWalletRepository.findById(userWalletDto.getId())).thenReturn(Optional.of(existingUserWallet));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userWalletDto, existingUserWallet))
                        .thenAnswer(invocation -> null);

            when(userWalletRepository.save(existingUserWallet)).thenReturn(existingUserWallet);

            boolean result = userWalletService.updateUserWallet(userWalletDto);

            assertTrue(result);
            verify(userWalletRepository, times(1)).findById(userWalletDto.getId());
            verify(userWalletRepository, times(1)).save(existingUserWallet);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userWalletDto, existingUserWallet), times(1));
        }
    }


    @Test
    void testUpdateUserWalletNotFound() {
        UserWalletDto userWalletDto = new UserWalletDto();
        userWalletDto.setId(1);

        when(userWalletRepository.findById(userWalletDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userWalletService.updateUserWallet(userWalletDto));
        verify(userWalletRepository, times(1)).findById(userWalletDto.getId());
    }

    @Test
    void testDeleteUserWallet() {
        int id = 1;
        UserWallet userWallet = new UserWallet();
        userWallet.setId(id);

        when(userWalletRepository.findById(id)).thenReturn(Optional.of(userWallet));
        doNothing().when(userWalletRepository).deleteById(id);

        boolean result = userWalletService.deleteUserWallet(id);

        assertTrue(result);
        verify(userWalletRepository, times(1)).findById(id);
        verify(userWalletRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserWalletNotFound() {
        int id = 1;

        when(userWalletRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userWalletService.deleteUserWallet(id));
        verify(userWalletRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUserWallets() {
        List<UserWallet> userWalletList = new ArrayList<>();
        userWalletList.add(new UserWallet());
        userWalletList.add(new UserWallet());

        when(userWalletRepository.findAll()).thenReturn(userWalletList);

        List<UserWallet> result = userWalletService.fetchAllUserWallets();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userWalletRepository, times(1)).findAll();
    }



}
