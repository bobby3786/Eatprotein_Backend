package com.usercart.sevice.impl;

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

import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;
import com.usercart.dto.UserCartDto;
import com.usercart.entity.UserCart;
import com.usercart.repository.UserCartRepository;
import com.usercart.service.impl.UserCartServiceImpl;



@ExtendWith(MockitoExtension.class)
public class UserCartServiceImplTest {

	@Mock
    private UserCartRepository userCartRepository;

    @InjectMocks
    private UserCartServiceImpl userCartService;
    

    @Test
    void testCreateUserCart() {
    	
    	 UserCartDto userCartDto = new UserCartDto();
    	 userCartDto.setPriceId(1);
		  userCartDto.setQuantity(10);
        
        
        UserCart userCart = new UserCart();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userCartDto, userCart))
                        .thenAnswer(invocation -> null);

            when(userCartRepository.save(any(UserCart.class))).thenReturn(userCart);

            userCartService.createUserCart(userCartDto);

            verify(userCartRepository, times(1)).save(any(UserCart.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userCartDto, userCart), times(1));
        }
    }
    
    @Test
    void testCreateUserCart_NullDto() {
       
        UserCartDto userCartDto = null;

        assertThrows(IllegalArgumentException.class, () -> userCartService.createUserCart(userCartDto));
        verify(userCartRepository, never()).save(any(UserCart.class));
    }
    
    @Test
    void testFetchUserCart() {
     
        int id = 1; 

        UserCart userCart = new UserCart();
        userCart.setId(id);
        userCart.setPriceId(1);
        userCart.setQuantity(10);


       when(userCartRepository.findById(id)).thenReturn(Optional.of(userCart));

        UserCart result = userCartService.fetchUserCart(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(1, result.getPriceId()); 
        assertEquals(10, result.getQuantity()); 
        verify(userCartRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUserCartNotFound() {
        int id = 1;

        when(userCartRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userCartService.fetchUserCart(id));
        verify(userCartRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserCart() {
        
    	UserCartDto userCartDto = new UserCartDto();
    	userCartDto.setId(1);
    	userCartDto.setPriceId(1);
		  userCartDto.setQuantity(10);

        UserCart existingUserCart = new UserCart();
        existingUserCart.setId(1); 
        existingUserCart.setPriceId(1);
        existingUserCart.setQuantity(10);

        when(userCartRepository.findById(userCartDto.getId())).thenReturn(Optional.of(existingUserCart));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userCartDto, existingUserCart))
                        .thenAnswer(invocation -> null);

            when(userCartRepository.save(existingUserCart)).thenReturn(existingUserCart);

            boolean result = userCartService.updateUserCart(userCartDto);

            assertTrue(result);
            verify(userCartRepository, times(1)).findById(userCartDto.getId());
            verify(userCartRepository, times(1)).save(existingUserCart);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userCartDto, existingUserCart), times(1));
        }
    }


    @Test
    void testUpdateUserCartNotFound() {
        UserCartDto userCartDto = new UserCartDto();
        userCartDto.setId(1);

        when(userCartRepository.findById(userCartDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userCartService.updateUserCart(userCartDto));
        verify(userCartRepository, times(1)).findById(userCartDto.getId());
    }

    @Test
    void testDeleteUserCart() {
        int id = 1;
        UserCart userCart = new UserCart();
        userCart.setId(id);

        when(userCartRepository.findById(id)).thenReturn(Optional.of(userCart));
        doNothing().when(userCartRepository).deleteById(id);

        boolean result = userCartService.deleteUserCart(id);

        assertTrue(result);
        verify(userCartRepository, times(1)).findById(id);
        verify(userCartRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserCartNotFound() {
        int id = 1;

        when(userCartRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userCartService.deleteUserCart(id));
        verify(userCartRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUserCarts() {
        List<UserCart> userCartList = new ArrayList<>();
        userCartList.add(new UserCart());
        userCartList.add(new UserCart());

        when(userCartRepository.findAll()).thenReturn(userCartList);

        List<UserCart> result = userCartService.fetchAllUserCarts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userCartRepository, times(1)).findAll();
    }


	
}
