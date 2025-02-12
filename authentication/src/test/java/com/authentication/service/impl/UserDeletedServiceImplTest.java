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

import com.authentication.dto.UserDeletedDto;
import com.authentication.entity.UserDeleted;
import com.authentication.repository.UserDeletedRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class UserDeletedServiceImplTest {
	
	@Mock
    private UserDeletedRepository userDeletedRepository;

    @InjectMocks
    private UserDeletedServiceImpl userDeletedService;
    

    @Test
    void testCreateUserDeleted() {
    	
    	 UserDeletedDto userDeletedDto = new UserDeletedDto();
    	 userDeletedDto.setPhone("9182291822");
        
        
        UserDeleted userDeleted = new UserDeleted();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userDeletedDto, userDeleted))
                        .thenAnswer(invocation -> null);

            when(userDeletedRepository.save(any(UserDeleted.class))).thenReturn(userDeleted);

            userDeletedService.createUserDeleted(userDeletedDto);

            verify(userDeletedRepository, times(1)).save(any(UserDeleted.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userDeletedDto, userDeleted), times(1));
        }
    }
    
    @Test
    void testCreateUserDeleted_NullDto() {
       
        UserDeletedDto userDeletedDto = null;

        assertThrows(IllegalArgumentException.class, () -> userDeletedService.createUserDeleted(userDeletedDto));
        verify(userDeletedRepository, never()).save(any(UserDeleted.class));
    }
    
    @Test
    void testFetchUserDeleted() {
     
        int id = 1; 

        UserDeleted userDeleted = new UserDeleted();
        userDeleted.setId(id);
        userDeleted.setPhone("9182291822");


       when(userDeletedRepository.findById(id)).thenReturn(Optional.of(userDeleted));

        UserDeleted result = userDeletedService.fetchUserDeleted(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("9182291822", result.getPhone()); 
        verify(userDeletedRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUserDeletedNotFound() {
        int id = 1;

        when(userDeletedRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userDeletedService.fetchUserDeleted(id));
        verify(userDeletedRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserDeleted() {
        
    	UserDeletedDto userDeletedDto = new UserDeletedDto();
    	userDeletedDto.setId(1);
    	userDeletedDto.setPhone("9182291822");

        UserDeleted existingUserDeleted = new UserDeleted();
        existingUserDeleted.setId(1); 
        existingUserDeleted.setPhone("9182291822");

        when(userDeletedRepository.findById(userDeletedDto.getId())).thenReturn(Optional.of(existingUserDeleted));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userDeletedDto, existingUserDeleted))
                        .thenAnswer(invocation -> null);

            when(userDeletedRepository.save(existingUserDeleted)).thenReturn(existingUserDeleted);

            boolean result = userDeletedService.updateUserDeleted(userDeletedDto);

            assertTrue(result);
            verify(userDeletedRepository, times(1)).findById(userDeletedDto.getId());
            verify(userDeletedRepository, times(1)).save(existingUserDeleted);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userDeletedDto, existingUserDeleted), times(1));
        }
    }


    @Test
    void testUpdateUserDeletedNotFound() {
        UserDeletedDto userDeletedDto = new UserDeletedDto();
        userDeletedDto.setId(1);

        when(userDeletedRepository.findById(userDeletedDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userDeletedService.updateUserDeleted(userDeletedDto));
        verify(userDeletedRepository, times(1)).findById(userDeletedDto.getId());
    }

    @Test
    void testDeleteUserDeleted() {
        int id = 1;
        UserDeleted userDeleted = new UserDeleted();
        userDeleted.setId(id);

        when(userDeletedRepository.findById(id)).thenReturn(Optional.of(userDeleted));
        doNothing().when(userDeletedRepository).deleteById(id);

        boolean result = userDeletedService.deleteUserDeleted(id);

        assertTrue(result);
        verify(userDeletedRepository, times(1)).findById(id);
        verify(userDeletedRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserDeletedNotFound() {
        int id = 1;

        when(userDeletedRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userDeletedService.deleteUserDeleted(id));
        verify(userDeletedRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUserDeleteds() {
        List<UserDeleted> userDeletedList = new ArrayList<>();
        userDeletedList.add(new UserDeleted());
        userDeletedList.add(new UserDeleted());

        when(userDeletedRepository.findAll()).thenReturn(userDeletedList);

        List<UserDeleted> result = userDeletedService.fetchAllUsersDeleted();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userDeletedRepository, times(1)).findAll();
    }


}
