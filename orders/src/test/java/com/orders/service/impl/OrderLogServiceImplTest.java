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

import com.orders.dto.OrderLogDto;
import com.orders.entity.OrderLog;
import com.orders.repository.OrderLogRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class OrderLogServiceImplTest {
	
	    @Mock
	    private OrderLogRepository orderLogRepository;

	    @InjectMocks
	    private OrderLogServiceImpl orderLogService;
	    

	    @Test
	    void testCreateOrderLog() {
	    	
	    	 OrderLogDto orderLogDto = new OrderLogDto();
	    	 orderLogDto.setStoreId(10);
			  orderLogDto.setOrderId(10);
	        
	        
	        OrderLog orderLog = new OrderLog();
	        
	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderLogDto, orderLog))
	                        .thenAnswer(invocation -> null);

	            when(orderLogRepository.save(any(OrderLog.class))).thenReturn(orderLog);

	            orderLogService.createOrderLog(orderLogDto);

	            verify(orderLogRepository, times(1)).save(any(OrderLog.class));
	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderLogDto, orderLog), times(1));
	        }
	    }
	    
	    @Test
	    void testCreateOrderLog_NullDto() {
	       
	        OrderLogDto orderLogDto = null;

	        assertThrows(IllegalArgumentException.class, () -> orderLogService.createOrderLog(orderLogDto));
	        verify(orderLogRepository, never()).save(any(OrderLog.class));
	    }
	    
	    @Test
	    void testFetchOrderLog() {
	     
	    	String id = "1"; 

	        OrderLog orderLog = new OrderLog();
	        orderLog.setId(id);
	        orderLog.setStoreId(10);
	        orderLog.setOrderId(10);


	       when(orderLogRepository.findById(id)).thenReturn(Optional.of(orderLog));

	        OrderLog result = orderLogService.fetchOrderLog(id);

	        assertNotNull(result); 
	        assertEquals(id, result.getId());
	        assertEquals(10, result.getStoreId()); 
	        assertEquals(10, result.getOrderId()); 
	        verify(orderLogRepository, times(1)).findById(id); 
	    }


	    @Test
	    void testFetchOrderLogNotFound() {
	    	String id = "1";

	        when(orderLogRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderLogService.fetchOrderLog(id));
	        verify(orderLogRepository, times(1)).findById(id);
	    }

	    @Test
	    void testUpdateOrderLog() {
	        
	    	OrderLogDto orderLogDto = new OrderLogDto();
	    	orderLogDto.setId("1");
	    	orderLogDto.setStoreId(10);
			  orderLogDto.setOrderId(10);

	        OrderLog existingOrderLog = new OrderLog();
	        existingOrderLog.setId("1"); 
	        existingOrderLog.setStoreId(10);
	        existingOrderLog.setOrderId(10);

	        when(orderLogRepository.findById(orderLogDto.getId())).thenReturn(Optional.of(existingOrderLog));

	        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
	            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderLogDto, existingOrderLog))
	                        .thenAnswer(invocation -> null);

	            when(orderLogRepository.save(existingOrderLog)).thenReturn(existingOrderLog);

	            boolean result = orderLogService.updateOrderLog(orderLogDto);

	            assertTrue(result);
	            verify(orderLogRepository, times(1)).findById(orderLogDto.getId());
	            verify(orderLogRepository, times(1)).save(existingOrderLog);

	            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderLogDto, existingOrderLog), times(1));
	        }
	    }


	    @Test
	    void testUpdateOrderLogNotFound() {
	        OrderLogDto orderLogDto = new OrderLogDto();
	        orderLogDto.setId("1");

	        when(orderLogRepository.findById(orderLogDto.getId())).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderLogService.updateOrderLog(orderLogDto));
	        verify(orderLogRepository, times(1)).findById(orderLogDto.getId());
	    }

	    @Test
	    void testDeleteOrderLog() {
	    	String id = "1";
	        OrderLog orderLog = new OrderLog();
	        orderLog.setId(id);

	        when(orderLogRepository.findById(id)).thenReturn(Optional.of(orderLog));
	        doNothing().when(orderLogRepository).deleteById(id);

	        boolean result = orderLogService.deleteOrderLog(id);

	        assertTrue(result);
	        verify(orderLogRepository, times(1)).findById(id);
	        verify(orderLogRepository, times(1)).deleteById(id);
	    }

	    @Test
	    void testDeleteOrderLogNotFound() {
	    	String id = "1";

	        when(orderLogRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> orderLogService.deleteOrderLog(id));
	        verify(orderLogRepository, times(1)).findById(id);
	    }

	    @Test
	    void testFetchAllOrderLogs() {
	        List<OrderLog> orderLogList = new ArrayList<>();
	        orderLogList.add(new OrderLog());
	        orderLogList.add(new OrderLog());

	        when(orderLogRepository.findAll()).thenReturn(orderLogList);

	        List<OrderLog> result = orderLogService.fetchAllOrderLogs();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(orderLogRepository, times(1)).findAll();
	    }


}
