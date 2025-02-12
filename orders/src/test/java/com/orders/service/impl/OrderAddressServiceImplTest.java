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

import com.orders.dto.OrderAddressDto;
import com.orders.entity.OrderAddress;
import com.orders.repository.OrderAddressRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class OrderAddressServiceImplTest {
	
	   @Mock
	    private OrderAddressRepository orderAddressRepository;

	    @InjectMocks
	    private OrderAddressServiceImpl orderAddressService;
	    

	    @Test
	    void testCreateOrderAddress() {
	    	
	    	 OrderAddressDto orderAddressDto = new OrderAddressDto();
	    	 orderAddressDto.setAddress("kavali");
			  orderAddressDto.setOrderId(1);
	        
	        
	        OrderAddress orderAddress = new OrderAddress();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderAddressDto, orderAddress))
	                        .thenAnswer(invocation -> null);

	            when(orderAddressRepository.save(any(OrderAddress.class))).thenReturn(orderAddress);

	            orderAddressService.createOrderAddress(orderAddressDto);

	            verify(orderAddressRepository, times(1)).save(any(OrderAddress.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderAddressDto, orderAddress), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateOrderAddress_NullDto() {
	       
	        OrderAddressDto orderAddressDto = null;

	        assertThrows(IllegalArgumentException.class, () -> orderAddressService.createOrderAddress(orderAddressDto));
	        verify(orderAddressRepository, never()).save(any(OrderAddress.class));
	    }
	    
	    @Test
	    void testFetchOrderAddress() {
	     
	    	String id = "1"; 

	        OrderAddress orderAddress = new OrderAddress();
	        orderAddress.setId(id);
	        orderAddress.setAddress("kavali");
	        orderAddress.setOrderId(1);


	       when(orderAddressRepository.findById(id)).thenReturn(Optional.of(orderAddress));

	        OrderAddress result = orderAddressService.fetchOrderAddress(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals("kavali", result.getAddress()); 
	        verify(orderAddressRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchOrderAddressNotFound() {
	    	String id = "1";

	        when(orderAddressRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderAddressService.fetchOrderAddress(id));
	        verify(orderAddressRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateOrderAddress() {
	        
	    	OrderAddressDto orderAddressDto = new OrderAddressDto();
	    	orderAddressDto.setId("1");
	    	 orderAddressDto.setAddress("kavali");
			  orderAddressDto.setOrderId(1);

	        OrderAddress existingOrderAddress = new OrderAddress();
	        existingOrderAddress.setId("1"); 
	        existingOrderAddress.setAddress("kavali");
	        existingOrderAddress.setOrderId(1);

	        when(orderAddressRepository.findById(orderAddressDto.getId())).thenReturn(Optional.of(existingOrderAddress));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderAddressDto, existingOrderAddress))
	                        .thenAnswer(invocation -> null);

	            when(orderAddressRepository.save(existingOrderAddress)).thenReturn(existingOrderAddress);

	            boolean result = orderAddressService.updateOrderAddress(orderAddressDto);

	            assertTrue(result);
	            verify(orderAddressRepository, times(1)).findById(orderAddressDto.getId());
	            verify(orderAddressRepository, times(1)).save(existingOrderAddress);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderAddressDto, existingOrderAddress), times(1));
	        }
	    }


	    @Test
	    void testUpdateOrderAddressNotFound() {
	        OrderAddressDto orderAddressDto = new OrderAddressDto();
	        orderAddressDto.setId("1");

	        when(orderAddressRepository.findById(orderAddressDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderAddressService.updateOrderAddress(orderAddressDto));
	        verify(orderAddressRepository, times(1)).findById(orderAddressDto.getId());
	    }

	    @Test
	    void testDeleteOrderAddress() {
	    	String id = "1";
	        OrderAddress orderAddress = new OrderAddress();
	        orderAddress.setId(id);

	        when(orderAddressRepository.findById(id)).thenReturn(Optional.of(orderAddress));
	        doNothing().when(orderAddressRepository).deleteById(id);

	        boolean result = orderAddressService.deleteOrderAddress(id);

	        assertTrue(result);
	        verify(orderAddressRepository, times(1)).findById(id);
	        verify(orderAddressRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteOrderAddressNotFound() {
	    	String id = "1";

	        when(orderAddressRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderAddressService.deleteOrderAddress(id));
	        verify(orderAddressRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllOrderAddresss() {
	        List<OrderAddress> orderAddressList = new ArrayList<>();
	        orderAddressList.add(new OrderAddress());
	        orderAddressList.add(new OrderAddress());

	        when(orderAddressRepository.findAll()).thenReturn(orderAddressList);

	        List<OrderAddress> result = orderAddressService.fetchAllOrderAddresses();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(orderAddressRepository, times(1)).findAll();
	    }


}
