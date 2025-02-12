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

import com.authentication.dto.DeletedUsersDto;
import com.authentication.entity.DeletedUsers;
import com.authentication.repository.DeletedUsersRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class DeletedUsersServiceImplTest {
	
	    @Mock
	    private DeletedUsersRepository deletedUsersRepository;

	    @InjectMocks
	    private DeletedUsersServiceImpl deletedUsersService;
	    

	    @Test
	    void testCreateDeletedUsers() {
	    	
	    	 DeletedUsersDto deletedUsersDto = new DeletedUsersDto();
	    	 deletedUsersDto.setName("xyz");
			  deletedUsersDto.setNumber("123");
	        
	        
	        DeletedUsers deletedUsers = new DeletedUsers();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(deletedUsersDto, deletedUsers))
	                        .thenAnswer(invocation -> null);

	            when(deletedUsersRepository.save(any(DeletedUsers.class))).thenReturn(deletedUsers);

	            deletedUsersService.createDeletedUsers(deletedUsersDto);

	            verify(deletedUsersRepository, times(1)).save(any(DeletedUsers.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(deletedUsersDto, deletedUsers), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateDeletedUsers_NullDto() {
	       
	        DeletedUsersDto deletedUsersDto = null;

	        assertThrows(IllegalArgumentException.class, () -> deletedUsersService.createDeletedUsers(deletedUsersDto));
	        verify(deletedUsersRepository, never()).save(any(DeletedUsers.class));
	    }
	    
	    @Test
	    void testFetchDeletedUsers() {
	     
	        int id = 1; 

	        DeletedUsers deletedUsers = new DeletedUsers();
	        deletedUsers.setId(id);
	        deletedUsers.setName("xyz");
	        deletedUsers.setNumber("123");


	       when(deletedUsersRepository.findById(id)).thenReturn(Optional.of(deletedUsers));

	        DeletedUsers result = deletedUsersService.fetchDeletedUsers(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("xyz", result.getName()); 
	        assertEquals("123", result.getNumber()); 
	        verify(deletedUsersRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchDeletedUsersNotFound() {
	        int id = 1;

	        when(deletedUsersRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> deletedUsersService.fetchDeletedUsers(id));
	        verify(deletedUsersRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateDeletedUsers() {
	        
	    	DeletedUsersDto deletedUsersDto = new DeletedUsersDto();
	    	deletedUsersDto.setId(1);
		      deletedUsersDto.setName("xyz");
			  deletedUsersDto.setNumber("123");

	        DeletedUsers existingDeletedUsers = new DeletedUsers();
	        existingDeletedUsers.setId(1); 
	        existingDeletedUsers.setName("xyz");
	        existingDeletedUsers.setNumber("123");

	        when(deletedUsersRepository.findById(deletedUsersDto.getId())).thenReturn(Optional.of(existingDeletedUsers));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(deletedUsersDto, existingDeletedUsers))
	                        .thenAnswer(invocation -> null);

	            when(deletedUsersRepository.save(existingDeletedUsers)).thenReturn(existingDeletedUsers);

	            boolean result = deletedUsersService.updateDeletedUsers(deletedUsersDto);

	            assertTrue(result);
	            verify(deletedUsersRepository, times(1)).findById(deletedUsersDto.getId());
	            verify(deletedUsersRepository, times(1)).save(existingDeletedUsers);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(deletedUsersDto, existingDeletedUsers), times(1));
	        }
	    }


	    @Test
	    void testUpdateDeletedUsersNotFound() {
	        DeletedUsersDto deletedUsersDto = new DeletedUsersDto();
	        deletedUsersDto.setId(1);

	        when(deletedUsersRepository.findById(deletedUsersDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> deletedUsersService.updateDeletedUsers(deletedUsersDto));
	        verify(deletedUsersRepository, times(1)).findById(deletedUsersDto.getId());
	    }

	    @Test
	    void testDeleteDeletedUsers() {
	        int id = 1;
	        DeletedUsers deletedUsers = new DeletedUsers();
	        deletedUsers.setId(id);

	        when(deletedUsersRepository.findById(id)).thenReturn(Optional.of(deletedUsers));
	        doNothing().when(deletedUsersRepository).deleteById(id);

	        boolean result = deletedUsersService.deleteDeletedUsers(id);

	        assertTrue(result);
	        verify(deletedUsersRepository, times(1)).findById(id);
	        verify(deletedUsersRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteDeletedUsersNotFound() {
	        int id = 1;

	        when(deletedUsersRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> deletedUsersService.deleteDeletedUsers(id));
	        verify(deletedUsersRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllDeletedUserss() {
	        List<DeletedUsers> deletedUsersList = new ArrayList<>();
	        deletedUsersList.add(new DeletedUsers());
	        deletedUsersList.add(new DeletedUsers());

	        when(deletedUsersRepository.findAll()).thenReturn(deletedUsersList);

	        List<DeletedUsers> result = deletedUsersService.fetchAllDeletedUsers();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(deletedUsersRepository, times(1)).findAll();
	    }


}
