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

import com.orders.dto.OrderItemDto;
import com.orders.entity.OrderItem;
import com.orders.repository.OrderItemRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {
	
	  @Mock
	    private OrderItemRepository orderItemRepository;

	    @InjectMocks
	    private OrderItemServiceImpl orderItemService;
	    

	    @Test
	    void testCreateOrderItem() {
	    	
	    	 OrderItemDto orderItemDto = new OrderItemDto();
	    	  orderItemDto.setPrice(500);
			  orderItemDto.setOrderId(1);

	        
	        
	        OrderItem orderItem = new OrderItem();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderItemDto, orderItem))
	                        .thenAnswer(invocation -> null);

	            when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

	            orderItemService.createOrderItem(orderItemDto);

	            verify(orderItemRepository, times(1)).save(any(OrderItem.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderItemDto, orderItem), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateOrderItem_NullDto() {
	       
	        OrderItemDto orderItemDto = null;

	        assertThrows(IllegalArgumentException.class, () -> orderItemService.createOrderItem(orderItemDto));
	        verify(orderItemRepository, never()).save(any(OrderItem.class));
	    }
	    
	    @Test
	    void testFetchOrderItem() {
	     
	    	String id = "1"; 

	        OrderItem orderItem = new OrderItem();
	        orderItem.setId(id);
	        orderItem.setPrice(500);
	        orderItem.setOrderId(1);



	       when(orderItemRepository.findById(id)).thenReturn(Optional.of(orderItem));

	        OrderItem result = orderItemService.fetchOrderItem(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals(500, result.getPrice()); 
	        assertEquals(1, result.getOrderId()); 
	        verify(orderItemRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchOrderItemNotFound() {
	    	String id = "1";

	        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderItemService.fetchOrderItem(id));
	        verify(orderItemRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateOrderItem() {
	        
	    	OrderItemDto orderItemDto = new OrderItemDto();
	    	orderItemDto.setId("1");
	    	  orderItemDto.setPrice(500);
			  orderItemDto.setOrderId(1);


	        OrderItem existingOrderItem = new OrderItem();
	        existingOrderItem.setId("1"); 
	        existingOrderItem.setPrice(500);
	        existingOrderItem.setOrderId(1);


	        when(orderItemRepository.findById(orderItemDto.getId())).thenReturn(Optional.of(existingOrderItem));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderItemDto, existingOrderItem))
	                        .thenAnswer(invocation -> null);

	            when(orderItemRepository.save(existingOrderItem)).thenReturn(existingOrderItem);

	            boolean result = orderItemService.updateOrderItem(orderItemDto);

	            assertTrue(result);
	            verify(orderItemRepository, times(1)).findById(orderItemDto.getId());
	            verify(orderItemRepository, times(1)).save(existingOrderItem);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderItemDto, existingOrderItem), times(1));
	        }
	    }


	    @Test
	    void testUpdateOrderItemNotFound() {
	        OrderItemDto orderItemDto = new OrderItemDto();
	        orderItemDto.setId("1");

	        when(orderItemRepository.findById(orderItemDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderItemService.updateOrderItem(orderItemDto));
	        verify(orderItemRepository, times(1)).findById(orderItemDto.getId());
	    }

	    @Test
	    void testDeleteOrderItem() {
	    	String id = "1";
	        OrderItem orderItem = new OrderItem();
	        orderItem.setId(id);

	        when(orderItemRepository.findById(id)).thenReturn(Optional.of(orderItem));
	        doNothing().when(orderItemRepository).deleteById(id);

	        boolean result = orderItemService.deleteOrderItem(id);

	        assertTrue(result);
	        verify(orderItemRepository, times(1)).findById(id);
	        verify(orderItemRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteOrderItemNotFound() {
	    	String id = "1";

	        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderItemService.deleteOrderItem(id));
	        verify(orderItemRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllOrderItems() {
	        List<OrderItem> orderItemList = new ArrayList<>();
	        orderItemList.add(new OrderItem());
	        orderItemList.add(new OrderItem());

	        when(orderItemRepository.findAll()).thenReturn(orderItemList);

	        List<OrderItem> result = orderItemService.fetchAllOrderItems();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(orderItemRepository, times(1)).findAll();
	    }


}
