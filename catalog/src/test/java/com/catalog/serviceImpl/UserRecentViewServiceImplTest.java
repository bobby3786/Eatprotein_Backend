package com.catalog.serviceImpl;

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

import com.catalog.dto.UserRecentViewDto;
import com.catalog.entity.UserRecentView;
import com.catalog.repository.UserRecentViewRepository;
import com.catalog.service.impl.UserRecentViewServiceImpl;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class UserRecentViewServiceImplTest {
	
	@Mock
    private UserRecentViewRepository userRecentViewRepository;

    @InjectMocks
    private UserRecentViewServiceImpl userRecentViewService;
    

    @Test
    void testCreateUserRecentView() {
    	
    	 UserRecentViewDto userRecentViewDto = new UserRecentViewDto();
    	 userRecentViewDto.setStoreId(10);
        
        
        UserRecentView UserRecentView = new UserRecentView();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userRecentViewDto, UserRecentView))
                        .thenAnswer(invocation -> null);

            when(userRecentViewRepository.save(any(UserRecentView.class))).thenReturn(UserRecentView);

            userRecentViewService.createUserRecentView(userRecentViewDto);

            verify(userRecentViewRepository, times(1)).save(any(UserRecentView.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userRecentViewDto, UserRecentView), times(1));
        }
    }
    
    @Test
    void testCreateUserRecentView_NullDto() {
       
        UserRecentViewDto userRecentViewDto = null;

        assertThrows(IllegalArgumentException.class, () -> userRecentViewService.createUserRecentView(userRecentViewDto));
        verify(userRecentViewRepository, never()).save(any(UserRecentView.class));
    }
    
    @Test
    void testFetchUserRecentView() {
     
        int id = 1; 

        UserRecentView userRecentView = new UserRecentView();
        userRecentView.setId(id);
        userRecentView.setStoreId(10);


       when(userRecentViewRepository.findById(id)).thenReturn(Optional.of(userRecentView));

        UserRecentView result = userRecentViewService.fetchUserRecentView(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(10, result.getStoreId()); 
        verify(userRecentViewRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUserRecentViewNotFound() {
        int id = 1;

        when(userRecentViewRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userRecentViewService.fetchUserRecentView(id));
        verify(userRecentViewRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserRecentView() {
        
    	UserRecentViewDto userRecentViewDto = new UserRecentViewDto();
    	userRecentViewDto.setId(1);
    	 userRecentViewDto.setStoreId(10);

        UserRecentView existingUserRecentView = new UserRecentView();
        existingUserRecentView.setId(1); 
        existingUserRecentView.setStoreId(10);

        when(userRecentViewRepository.findById(userRecentViewDto.getId())).thenReturn(Optional.of(existingUserRecentView));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userRecentViewDto, existingUserRecentView))
                        .thenAnswer(invocation -> null);

            when(userRecentViewRepository.save(existingUserRecentView)).thenReturn(existingUserRecentView);

            boolean result = userRecentViewService.updateUserRecentView(userRecentViewDto);

            assertTrue(result);
            verify(userRecentViewRepository, times(1)).findById(userRecentViewDto.getId());
            verify(userRecentViewRepository, times(1)).save(existingUserRecentView);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userRecentViewDto, existingUserRecentView), times(1));
        }
    }


    @Test
    void testUpdateUserRecentViewNotFound() {
        UserRecentViewDto userRecentViewDto = new UserRecentViewDto();
        userRecentViewDto.setId(1);

        when(userRecentViewRepository.findById(userRecentViewDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userRecentViewService.updateUserRecentView(userRecentViewDto));
        verify(userRecentViewRepository, times(1)).findById(userRecentViewDto.getId());
    }

    @Test
    void testDeleteUserRecentView() {
        int id = 1;
        UserRecentView userRecentView = new UserRecentView();
        userRecentView.setId(id);

        when(userRecentViewRepository.findById(id)).thenReturn(Optional.of(userRecentView));
        doNothing().when(userRecentViewRepository).deleteById(id);

        boolean result = userRecentViewService.deleteUserRecentView(id);

        assertTrue(result);
        verify(userRecentViewRepository, times(1)).findById(id);
        verify(userRecentViewRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserRecentViewNotFound() {
        int id = 1;

        when(userRecentViewRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userRecentViewService.deleteUserRecentView(id));
        verify(userRecentViewRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUserRecentViews() {
        List<UserRecentView> userRecentViewList = new ArrayList<>();
        userRecentViewList.add(new UserRecentView());
        userRecentViewList.add(new UserRecentView());

        when(userRecentViewRepository.findAll()).thenReturn(userRecentViewList);

        List<UserRecentView> result = userRecentViewService.fetchAllUserRecentViews();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRecentViewRepository, times(1)).findAll();
    }



}
