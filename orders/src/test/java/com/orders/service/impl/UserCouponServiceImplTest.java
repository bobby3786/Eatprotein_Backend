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

import com.orders.dto.UserCouponDto;
import com.orders.entity.UserCoupon;
import com.orders.repository.UserCouponRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;


@ExtendWith(MockitoExtension.class)
public class UserCouponServiceImplTest {
	
	@Mock
    private UserCouponRepository userCouponRepository;

    @InjectMocks
    private UserCouponServiceImpl userCouponService;
    

    @Test
    void testCreateUserCoupon() {
    	
    	 UserCouponDto userCouponDto = new UserCouponDto();
    	 userCouponDto.setStatus("active");
		  userCouponDto.setOrderId(1);
        
        
        UserCoupon userCoupon = new UserCoupon();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userCouponDto, userCoupon))
                        .thenAnswer(invocation -> null);

            when(userCouponRepository.save(any(UserCoupon.class))).thenReturn(userCoupon);

            userCouponService.createUserCoupon(userCouponDto);

            verify(userCouponRepository, times(1)).save(any(UserCoupon.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userCouponDto, userCoupon), times(1));
        }
    }
    
    @Test
    void testCreateUserCoupon_NullDto() {
       
        UserCouponDto userCouponDto = null;

        assertThrows(IllegalArgumentException.class, () -> userCouponService.createUserCoupon(userCouponDto));
        verify(userCouponRepository, never()).save(any(UserCoupon.class));
    }
    
    @Test
    void testFetchUserCoupon() {
     
    	String id = "1"; 

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setId(id);
        userCoupon.setStatus("active");
        userCoupon.setOrderId(1);


       when(userCouponRepository.findById(id)).thenReturn(Optional.of(userCoupon));

        UserCoupon result = userCouponService.fetchUserCoupon(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("active", result.getStatus()); 
        assertEquals(1, result.getOrderId()); 
        verify(userCouponRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchUserCouponNotFound() {
    	String id = "1";

        when(userCouponRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userCouponService.fetchUserCoupon(id));
        verify(userCouponRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateUserCoupon() {
        
    	UserCouponDto userCouponDto = new UserCouponDto();
    	userCouponDto.setId("1");
    	 userCouponDto.setStatus("active");
		  userCouponDto.setOrderId(1);

        UserCoupon existingUserCoupon = new UserCoupon();
        existingUserCoupon.setId("1"); 
        existingUserCoupon.setStatus("active");
        existingUserCoupon.setOrderId(1);

        when(userCouponRepository.findById(userCouponDto.getId())).thenReturn(Optional.of(existingUserCoupon));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(userCouponDto, existingUserCoupon))
                        .thenAnswer(invocation -> null);

            when(userCouponRepository.save(existingUserCoupon)).thenReturn(existingUserCoupon);

            boolean result = userCouponService.updateUserCoupon(userCouponDto);

            assertTrue(result);
            verify(userCouponRepository, times(1)).findById(userCouponDto.getId());
            verify(userCouponRepository, times(1)).save(existingUserCoupon);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(userCouponDto, existingUserCoupon), times(1));
        }
    }


    @Test
    void testUpdateUserCouponNotFound() {
        UserCouponDto userCouponDto = new UserCouponDto();
        userCouponDto.setId("1");

        when(userCouponRepository.findById(userCouponDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userCouponService.updateUserCoupon(userCouponDto));
        verify(userCouponRepository, times(1)).findById(userCouponDto.getId());
    }

    @Test
    void testDeleteUserCoupon() {
    	String id = "1";
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setId(id);

        when(userCouponRepository.findById(id)).thenReturn(Optional.of(userCoupon));
        doNothing().when(userCouponRepository).deleteById(id);

        boolean result = userCouponService.deleteUserCoupon(id);

        assertTrue(result);
        verify(userCouponRepository, times(1)).findById(id);
        verify(userCouponRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserCouponNotFound() {
    	String id = "1";

        when(userCouponRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userCouponService.deleteUserCoupon(id));
        verify(userCouponRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllUserCoupons() {
        List<UserCoupon> userCouponList = new ArrayList<>();
        userCouponList.add(new UserCoupon());
        userCouponList.add(new UserCoupon());

        when(userCouponRepository.findAll()).thenReturn(userCouponList);

        List<UserCoupon> result = userCouponService.fetchAllUserCoupons();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userCouponRepository, times(1)).findAll();
    }


}
