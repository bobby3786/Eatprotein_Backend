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

import com.payments.dto.PaymentDto;
import com.payments.entity.Payment;
import com.payments.repository.PaymentRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
	
	@Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;
    

    @Test
    void testCreatePayment() {
    	
    	 PaymentDto paymentDto = new PaymentDto();
    	 paymentDto.setDeliveryCharges(32.50);
		  paymentDto.setAppPayment(50);
        
        
        Payment payment = new Payment();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDto, payment))
                        .thenAnswer(invocation -> null);

            when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

            paymentService.createPayment(paymentDto);

            verify(paymentRepository, times(1)).save(any(Payment.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDto, payment), times(1));
        }
    }
    
    @Test
    void testCreatePayment_NullDto() {
       
        PaymentDto paymentDto = null;

        assertThrows(IllegalArgumentException.class, () -> paymentService.createPayment(paymentDto));
        verify(paymentRepository, never()).save(any(Payment.class));
    }
    
    @Test
    void testFetchPayment() {
     
        int id = 1; 

        Payment payment = new Payment();
        payment.setId(id);
        payment.setDeliveryCharges(32.50);
        payment.setAppPayment(50);


       when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        Payment result = paymentService.fetchPayment(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals(32.50, result.getDeliveryCharges()); 
        assertEquals(50, result.getAppPayment()); 
        verify(paymentRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchPaymentNotFound() {
        int id = 1;

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.fetchPayment(id));
        verify(paymentRepository, times(1)).findById(id);
    }

    @Test
    void testUpdatePayment() {
        
    	PaymentDto paymentDto = new PaymentDto();
    	paymentDto.setId(1);
    	 paymentDto.setDeliveryCharges(32.50);
		  paymentDto.setAppPayment(50);

        Payment existingPayment = new Payment();
        existingPayment.setId(1); 
        existingPayment.setDeliveryCharges(32.50);
        existingPayment.setAppPayment(50);

        when(paymentRepository.findById(paymentDto.getId())).thenReturn(Optional.of(existingPayment));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDto, existingPayment))
                        .thenAnswer(invocation -> null);

            when(paymentRepository.save(existingPayment)).thenReturn(existingPayment);

            boolean result = paymentService.updatePayment(paymentDto);

            assertTrue(result);
            verify(paymentRepository, times(1)).findById(paymentDto.getId());
            verify(paymentRepository, times(1)).save(existingPayment);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(paymentDto, existingPayment), times(1));
        }
    }


    @Test
    void testUpdatePaymentNotFound() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(1);

        when(paymentRepository.findById(paymentDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(paymentDto));
        verify(paymentRepository, times(1)).findById(paymentDto.getId());
    }

    @Test
    void testDeletePayment() {
        int id = 1;
        Payment payment = new Payment();
        payment.setId(id);

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        doNothing().when(paymentRepository).deleteById(id);

        boolean result = paymentService.deletePayment(id);

        assertTrue(result);
        verify(paymentRepository, times(1)).findById(id);
        verify(paymentRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePaymentNotFound() {
        int id = 1;

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.deletePayment(id));
        verify(paymentRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllPayments() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment());
        paymentList.add(new Payment());

        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> result = paymentService.fetchAllPayments();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAll();
    }


}
