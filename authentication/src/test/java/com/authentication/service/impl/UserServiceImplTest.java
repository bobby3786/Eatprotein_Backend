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

import com.authentication.dto.UserDto;
import com.authentication.entity.User;
import com.authentication.repository.UserRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	    @Mock
	    private UserRepository userRepository;

	    @InjectMocks
	    private UserServiceImpl userService;
	    

	    @Test
	    void testCreateUser() {
	    	
	    	 UserDto userDto = new UserDto();
	    	 userDto.setName("Bobby");
			  userDto.setCity("kavali");
	        
	        
	        User user = new User();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userDto, user))
	                        .thenAnswer(invocation -> null);

	            when(userRepository.save(any(User.class))).thenReturn(user);

	            userService.createUser(userDto);

	            verify(userRepository, times(1)).save(any(User.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userDto, user), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateUser_NullDto() {
	       
	        UserDto userDto = null;

	        assertThrows(IllegalArgumentException.class, () -> userService.createUser(userDto));
	        verify(userRepository, never()).save(any(User.class));
	    }
	    
	    @Test
	    void testFetchUser() {
	     
	        int id = 1; 

	        User user = new User();
	        user.setId(id);
	        user.setName("Bobby");
	        user.setCity("kavali");


	       when(userRepository.findById(id)).thenReturn(Optional.of(user));

	        User result = userService.fetchUser(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("Bobby", result.getName()); 
	        assertEquals("kavali", result.getCity()); 
	        verify(userRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchUserNotFound() {
	        int id = 1;

	        when(userRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> userService.fetchUser(id));
	        verify(userRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateUser() {
	        
	    	UserDto userDto = new UserDto();
	    	userDto.setId(1);
	    	userDto.setName("Bobby");
			  userDto.setCity("kavali");

	        User existingUser = new User();
	        existingUser.setId(1); 
	        existingUser.setName("Bobby");
	        existingUser.setCity("kavali");

	        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(existingUser));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userDto, existingUser))
	                        .thenAnswer(invocation -> null);

	            when(userRepository.save(existingUser)).thenReturn(existingUser);

	            boolean result = userService.updateUser(userDto);

	            assertTrue(result);
	            verify(userRepository, times(1)).findById(userDto.getId());
	            verify(userRepository, times(1)).save(existingUser);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userDto, existingUser), times(1));
	        }
	    }


	    @Test
	    void testUpdateUserNotFound() {
	        UserDto userDto = new UserDto();
	        userDto.setId(1);

	        when(userRepository.findById(userDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userDto));
	        verify(userRepository, times(1)).findById(userDto.getId());
	    }

	    @Test
	    void testDeleteUser() {
	        int id = 1;
	        User user = new User();
	        user.setId(id);

	        when(userRepository.findById(id)).thenReturn(Optional.of(user));
	        doNothing().when(userRepository).deleteById(id);

	        boolean result = userService.deleteUser(id);

	        assertTrue(result);
	        verify(userRepository, times(1)).findById(id);
	        verify(userRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteUserNotFound() {
	        int id = 1;

	        when(userRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(id));
	        verify(userRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllUsers() {
	        List<User> userList = new ArrayList<>();
	        userList.add(new User());
	        userList.add(new User());

	        when(userRepository.findAll()).thenReturn(userList);

	        List<User> result = userService.fetchAllUsers();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(userRepository, times(1)).findAll();
	    }



}
