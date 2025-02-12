package com.orders.service.impl;

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

import com.orders.dto.UserOrderDto;
import com.orders.entity.UserOrder;
import com.orders.repository.UserOrderRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class UserOrderServiceImplTest {
	
	@Mock
    private UserOrderRepository userOrderRepository;

    @InjectMocks
    private UserOrderServiceImpl userOrderService;
    

    @Test
    void testCreateUserOrder() {
    	
    	 UserOrderDto userOrderDto = new UserOrderDto();
    	 userOrderDto.setOrderStatus(1);
		  userOrderDto.setAppAmount(500);
        
        
        UserOrder userOrder = new UserOrder();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userOrderDto, userOrder))
                        .thenAnswer(invocation -> null);

            when(userOrderRepository.save(any(UserOrder.class))).thenReturn(userOrder);

            userOrderService.createUserOrder(userOrderDto);

            verify(userOrderRepository, times(1)).save(any(UserOrder.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userOrderDto, userOrder), times(1));
        }
    }
    
    @Test
    void testCreateUserOrder_NullDto() {
       
        UserOrderDto userOrderDto = null;

        assertThrows(IllegalArgumentException.class, () -> userOrderService.createUserOrder(userOrderDto));
        verify(userOrderRepository, never()).save(any(UserOrder.class));
    }
    
    @Test
    void testFetchUserOrder() {
     
    	String id = "1"; 

        UserOrder userOrder = new UserOrder();
        userOrder.setId(id);
        userOrder.setOrderStatus(1);
        userOrder.setAppAmount(500);


       when(userOrderRepository.findById(id)).thenReturn(Optional.of(userOrder));

        UserOrder result = userOrderService.fetchUserOrder(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(1, result.getOrderStatus()); 
        assertEquals(500, result.getAppAmount()); 
        verify(userOrderRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUserOrderNotFound() {
    	String id = "1";

        when(userOrderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userOrderService.fetchUserOrder(id));
        verify(userOrderRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserOrder() {
        
    	UserOrderDto userOrderDto = new UserOrderDto();
    	userOrderDto.setId("1");
    	 userOrderDto.setOrderStatus(1);
		  userOrderDto.setAppAmount(500);

        UserOrder existingUserOrder = new UserOrder();
        existingUserOrder.setId("1"); 
        existingUserOrder.setOrderStatus(1);
        existingUserOrder.setAppAmount(500);

        when(userOrderRepository.findById(userOrderDto.getId())).thenReturn(Optional.of(existingUserOrder));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userOrderDto, existingUserOrder))
                        .thenAnswer(invocation -> null);

            when(userOrderRepository.save(existingUserOrder)).thenReturn(existingUserOrder);

            boolean result = userOrderService.updateUserOrder(userOrderDto);

            assertTrue(result);
            verify(userOrderRepository, times(1)).findById(userOrderDto.getId());
            verify(userOrderRepository, times(1)).save(existingUserOrder);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userOrderDto, existingUserOrder), times(1));
        }
    }


    @Test
    void testUpdateUserOrderNotFound() {
        UserOrderDto userOrderDto = new UserOrderDto();
        userOrderDto.setId("1");

        when(userOrderRepository.findById(userOrderDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userOrderService.updateUserOrder(userOrderDto));
        verify(userOrderRepository, times(1)).findById(userOrderDto.getId());
    }

    @Test
    void testDeleteUserOrder() {
    	String id = "1";
        UserOrder userOrder = new UserOrder();
        userOrder.setId(id);

        when(userOrderRepository.findById(id)).thenReturn(Optional.of(userOrder));
        doNothing().when(userOrderRepository).deleteById(id);

        boolean result = userOrderService.deleteUserOrder(id);

        assertTrue(result);
        verify(userOrderRepository, times(1)).findById(id);
        verify(userOrderRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserOrderNotFound() {
    	String id = "1";

        when(userOrderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userOrderService.deleteUserOrder(id));
        verify(userOrderRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUserOrders() {
        List<UserOrder> userOrderList = new ArrayList<>();
        userOrderList.add(new UserOrder());
        userOrderList.add(new UserOrder());

        when(userOrderRepository.findAll()).thenReturn(userOrderList);

        List<UserOrder> result = userOrderService.fetchAllUserOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userOrderRepository, times(1)).findAll();
    }


}
