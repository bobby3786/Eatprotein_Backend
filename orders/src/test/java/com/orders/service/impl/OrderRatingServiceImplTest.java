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

import com.orders.dto.OrderRatingDto;
import com.orders.entity.OrderRating;
import com.orders.repository.OrderRatingRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class OrderRatingServiceImplTest {
	
	@Mock
    private OrderRatingRepository orderRatingRepository;

    @InjectMocks
    private OrderRatingServiceImpl orderRatingService;
    

    @Test
    void testCreateOrderRating() {
    	
    	 OrderRatingDto orderRatingDto = new OrderRatingDto();
    	 orderRatingDto.setStoreId(10);
		  orderRatingDto.setOrderId(1);
        
        
        OrderRating orderRating = new OrderRating();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderRatingDto, orderRating))
                        .thenAnswer(invocation -> null);

            when(orderRatingRepository.save(any(OrderRating.class))).thenReturn(orderRating);

            orderRatingService.createOrderRating(orderRatingDto);

            verify(orderRatingRepository, times(1)).save(any(OrderRating.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderRatingDto, orderRating), times(1));
        }
    }
    
    @Test
    void testCreateOrderRating_NullDto() {
       
        OrderRatingDto orderRatingDto = null;

        assertThrows(IllegalArgumentException.class, () -> orderRatingService.createOrderRating(orderRatingDto));
        verify(orderRatingRepository, never()).save(any(OrderRating.class));
    }
    
    @Test
    void testFetchOrderRating() {
     
    	String id = "1"; 

        OrderRating orderRating = new OrderRating();
        orderRating.setId(id);
        orderRating.setStoreId(10);
        orderRating.setOrderId(1);


       when(orderRatingRepository.findById(id)).thenReturn(Optional.of(orderRating));

        OrderRating result = orderRatingService.fetchOrderRating(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(10, result.getStoreId()); 
        assertEquals(1, result.getOrderId()); 
        verify(orderRatingRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchOrderRatingNotFound() {
    	String id = "1";

        when(orderRatingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderRatingService.fetchOrderRating(id));
        verify(orderRatingRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateOrderRating() {
        
    	OrderRatingDto orderRatingDto = new OrderRatingDto();
    	orderRatingDto.setId("1");
    	orderRatingDto.setStoreId(10);
		  orderRatingDto.setOrderId(1);

        OrderRating existingOrderRating = new OrderRating();
        existingOrderRating.setId("1"); 
        existingOrderRating.setStoreId(10);
        existingOrderRating.setOrderId(1);

        when(orderRatingRepository.findById(orderRatingDto.getId())).thenReturn(Optional.of(existingOrderRating));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(orderRatingDto, existingOrderRating))
                        .thenAnswer(invocation -> null);

            when(orderRatingRepository.save(existingOrderRating)).thenReturn(existingOrderRating);

            boolean result = orderRatingService.updateOrderRating(orderRatingDto);

            assertTrue(result);
            verify(orderRatingRepository, times(1)).findById(orderRatingDto.getId());
            verify(orderRatingRepository, times(1)).save(existingOrderRating);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(orderRatingDto, existingOrderRating), times(1));
        }
    }


    @Test
    void testUpdateOrderRatingNotFound() {
        OrderRatingDto orderRatingDto = new OrderRatingDto();
        orderRatingDto.setId("1");

        when(orderRatingRepository.findById(orderRatingDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderRatingService.updateOrderRating(orderRatingDto));
        verify(orderRatingRepository, times(1)).findById(orderRatingDto.getId());
    }

    @Test
    void testDeleteOrderRating() {
    	String id = "1";
        OrderRating OrderRating = new OrderRating();
        OrderRating.setId(id);

        when(orderRatingRepository.findById(id)).thenReturn(Optional.of(OrderRating));
        doNothing().when(orderRatingRepository).deleteById(id);

        boolean result = orderRatingService.deleteOrderRating(id);

        assertTrue(result);
        verify(orderRatingRepository, times(1)).findById(id);
        verify(orderRatingRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteOrderRatingNotFound() {
    	String id = "1";

        when(orderRatingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderRatingService.deleteOrderRating(id));
        verify(orderRatingRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllOrderRatings() {
        List<OrderRating> orderRatingList = new ArrayList<>();
        orderRatingList.add(new OrderRating());
        orderRatingList.add(new OrderRating());

        when(orderRatingRepository.findAll()).thenReturn(orderRatingList);

        List<OrderRating> result = orderRatingService.fetchAllOrderRatings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRatingRepository, times(1)).findAll();
    }


}
