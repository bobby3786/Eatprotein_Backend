package com.payments.service.impl;

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

import com.payments.dto.PaymentFoDto;
import com.payments.entity.PaymentFo;
import com.payments.repository.PaymentFoRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class PaymentFoServiceImplTest {

	@Mock
    private PaymentFoRepository paymentFoRepository;

    @InjectMocks
    private PaymentFoServiceImpl paymentFoService;
    

    @Test
    void testCreatePaymentFo() {
    	
    	 PaymentFoDto paymentFoDto = new PaymentFoDto();
    	 paymentFoDto.setDeliveryCharges(32.50);
		  paymentFoDto.setOrderAmount(500);
        
        
        PaymentFo paymentFo = new PaymentFo();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentFoDto, paymentFo))
                        .thenAnswer(invocation -> null);

            when(paymentFoRepository.save(any(PaymentFo.class))).thenReturn(paymentFo);

            paymentFoService.createPaymentFo(paymentFoDto);

            verify(paymentFoRepository, times(1)).save(any(PaymentFo.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentFoDto, paymentFo), times(1));
        }
    }
    
    @Test
    void testCreatePaymentFo_NullDto() {
       
        PaymentFoDto paymentFoDto = null;

        assertThrows(IllegalArgumentException.class, () -> paymentFoService.createPaymentFo(paymentFoDto));
        verify(paymentFoRepository, never()).save(any(PaymentFo.class));
    }
    
    @Test
    void testFetchPaymentFo() {
     
        int id = 1; 

        PaymentFo paymentFo = new PaymentFo();
        paymentFo.setId(id);
        paymentFo.setDeliveryCharges(32.50);
        paymentFo.setOrderAmount(500);


       when(paymentFoRepository.findById(id)).thenReturn(Optional.of(paymentFo));

        PaymentFo result = paymentFoService.fetchPaymentFo(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(32.50, result.getDeliveryCharges()); 
        assertEquals(500, result.getOrderAmount()); 
        verify(paymentFoRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchPaymentFoNotFound() {
        int id = 1;

        when(paymentFoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentFoService.fetchPaymentFo(id));
        verify(paymentFoRepository, times(1)).findById(id);
    }

    @Test
    void testUpdatePaymentFo() {
        
    	PaymentFoDto paymentFoDto = new PaymentFoDto();
    	paymentFoDto.setId(1);
    	 paymentFoDto.setDeliveryCharges(32.50);
		  paymentFoDto.setOrderAmount(500);

        PaymentFo existingPaymentFo = new PaymentFo();
        existingPaymentFo.setId(1); 
        existingPaymentFo.setDeliveryCharges(32.50);
        existingPaymentFo.setOrderAmount(500);

        when(paymentFoRepository.findById(paymentFoDto.getId())).thenReturn(Optional.of(existingPaymentFo));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentFoDto, existingPaymentFo))
                        .thenAnswer(invocation -> null);

            when(paymentFoRepository.save(existingPaymentFo)).thenReturn(existingPaymentFo);

            boolean result = paymentFoService.updatePaymentFo(paymentFoDto);

            assertTrue(result);
            verify(paymentFoRepository, times(1)).findById(paymentFoDto.getId());
            verify(paymentFoRepository, times(1)).save(existingPaymentFo);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentFoDto, existingPaymentFo), times(1));
        }
    }


    @Test
    void testUpdatePaymentFoNotFound() {
        PaymentFoDto PaymentFoDto = new PaymentFoDto();
        PaymentFoDto.setId(1);

        when(paymentFoRepository.findById(PaymentFoDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentFoService.updatePaymentFo(PaymentFoDto));
        verify(paymentFoRepository, times(1)).findById(PaymentFoDto.getId());
    }

    @Test
    void testDeletePaymentFo() {
        int id = 1;
        PaymentFo PaymentFo = new PaymentFo();
        PaymentFo.setId(id);

        when(paymentFoRepository.findById(id)).thenReturn(Optional.of(PaymentFo));
        doNothing().when(paymentFoRepository).deleteById(id);

        boolean result = paymentFoService.deletePaymentFo(id);

        assertTrue(result);
        verify(paymentFoRepository, times(1)).findById(id);
        verify(paymentFoRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePaymentFoNotFound() {
        int id = 1;

        when(paymentFoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentFoService.deletePaymentFo(id));
        verify(paymentFoRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllPaymentFos() {
        List<PaymentFo> paymentFoList = new ArrayList<>();
        paymentFoList.add(new PaymentFo());
        paymentFoList.add(new PaymentFo());

        when(paymentFoRepository.findAll()).thenReturn(paymentFoList);

        List<PaymentFo> result = paymentFoService.fetchAllPaymentFos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentFoRepository, times(1)).findAll();
    }

	
}
