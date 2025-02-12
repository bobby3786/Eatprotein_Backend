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

import com.orders.dto.OrderPaymentDto;
import com.orders.entity.OrderPayment;
import com.orders.repository.OrderPaymentRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class OrderPaymentServiceImplTest {

	@Mock
    private OrderPaymentRepository orderPaymentRepository;

    @InjectMocks
    private OrderPaymentServiceImpl orderPaymentService;
    

    @Test
    void testCreateOrderPayment() {
    	
    	 OrderPaymentDto orderPaymentDto = new OrderPaymentDto();
    	 orderPaymentDto.setAmount(500);
		  orderPaymentDto.setOrderId(1);
        
        
        OrderPayment orderPayment = new OrderPayment();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderPaymentDto, orderPayment))
                        .thenAnswer(invocation -> null);

            when(orderPaymentRepository.save(any(OrderPayment.class))).thenReturn(orderPayment);

            orderPaymentService.createOrderPayment(orderPaymentDto);

            verify(orderPaymentRepository, times(1)).save(any(OrderPayment.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderPaymentDto, orderPayment), times(1));
        }
    }
    
    @Test
    void testCreateOrderPayment_NullDto() {
       
        OrderPaymentDto orderPaymentDto = null;

        assertThrows(IllegalArgumentException.class, () -> orderPaymentService.createOrderPayment(orderPaymentDto));
        verify(orderPaymentRepository, never()).save(any(OrderPayment.class));
    }
    
    @Test
    void testFetchOrderPayment() {
     
    	String id = "1"; 

        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setId(id);
        orderPayment.setAmount(500);
        orderPayment.setOrderId(1);


       when(orderPaymentRepository.findById(id)).thenReturn(Optional.of(orderPayment));

        OrderPayment result = orderPaymentService.fetchOrderPayment(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(500, result.getAmount()); 
        assertEquals(1, result.getOrderId()); 
        verify(orderPaymentRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchOrderPaymentNotFound() {
    	String id = "1";

        when(orderPaymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderPaymentService.fetchOrderPayment(id));
        verify(orderPaymentRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateOrderPayment() {
        
    	OrderPaymentDto orderPaymentDto = new OrderPaymentDto();
    	orderPaymentDto.setId("1");
    	 orderPaymentDto.setAmount(500);
		  orderPaymentDto.setOrderId(1);

        OrderPayment existingOrderPayment = new OrderPayment();
        existingOrderPayment.setId("1"); 
        existingOrderPayment.setAmount(500);
        existingOrderPayment.setOrderId(1);

        when(orderPaymentRepository.findById(orderPaymentDto.getId())).thenReturn(Optional.of(existingOrderPayment));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderPaymentDto, existingOrderPayment))
                        .thenAnswer(invocation -> null);

            when(orderPaymentRepository.save(existingOrderPayment)).thenReturn(existingOrderPayment);

            boolean result = orderPaymentService.updateOrderPayment(orderPaymentDto);

            assertTrue(result);
            verify(orderPaymentRepository, times(1)).findById(orderPaymentDto.getId());
            verify(orderPaymentRepository, times(1)).save(existingOrderPayment);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderPaymentDto, existingOrderPayment), times(1));
        }
    }


    @Test
    void testUpdateOrderPaymentNotFound() {
        OrderPaymentDto orderPaymentDto = new OrderPaymentDto();
        orderPaymentDto.setId("1");

        when(orderPaymentRepository.findById(orderPaymentDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderPaymentService.updateOrderPayment(orderPaymentDto));
        verify(orderPaymentRepository, times(1)).findById(orderPaymentDto.getId());
    }

    @Test
    void testDeleteOrderPayment() {
    	String id = "1";
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setId(id);

        when(orderPaymentRepository.findById(id)).thenReturn(Optional.of(orderPayment));
        doNothing().when(orderPaymentRepository).deleteById(id);

        boolean result = orderPaymentService.deleteOrderPayment(id);

        assertTrue(result);
        verify(orderPaymentRepository, times(1)).findById(id);
        verify(orderPaymentRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteOrderPaymentNotFound() {
    	String id = "1";

        when(orderPaymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderPaymentService.deleteOrderPayment(id));
        verify(orderPaymentRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllOrderPayments() {
        List<OrderPayment> orderPaymentList = new ArrayList<>();
        orderPaymentList.add(new OrderPayment());
        orderPaymentList.add(new OrderPayment());

        when(orderPaymentRepository.findAll()).thenReturn(orderPaymentList);

        List<OrderPayment> result = orderPaymentService.fetchAllOrderPayments();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderPaymentRepository, times(1)).findAll();
    }

	
}
