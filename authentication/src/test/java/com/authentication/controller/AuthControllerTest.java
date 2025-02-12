package com.authentication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.authentication.dto.AuthRequest;
import com.authentication.entity.UserCredential;
import com.authentication.service.AuthService;



public class AuthControllerTest {
	
	 @InjectMocks
	    private AuthController authController;

	    @Mock
	    private AuthService authService;

	    @Mock
	    private AuthenticationManager authenticationManager;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    @Test
	    void testAddNewUser_ShouldReturnSavedMessage() {
	    	UserCredential user = new UserCredential();
	    	user.setName("Bobby");
	    	user.setPassword("1234");
	    	
	    	when(authService.saveUser(user)).thenReturn("User Saved Successfully");
	    	
	    	String result = authController.addNewUser(user);
	    	
	    	assertEquals("User Saved Successfully", result);
	        verify(authService,times(1)).saveUser(user);
	    }
	    
	    @Test
	    void testGetToken_ShouldReturnToken_WhenAuthenticationIsSuccessful() {
	       
	        AuthRequest authRequest = new AuthRequest("Bobby", "1234");

	        Authentication authentication = mock(Authentication.class);
	        when(authentication.isAuthenticated()).thenReturn(true);

	        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
	                .thenReturn(authentication);

	        when(authService.generateToken(authRequest.getUsername())).thenReturn("testToken");

	        String result = authController.getToken(authRequest);

	        assertEquals("testToken", result);
	        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
	        verify(authService, times(1)).generateToken(authRequest.getUsername());
	    }

	    
	    @Test
	    void testGetToken_ShouldThrowException_WhenAuthenticationFails() {
	      
	        AuthRequest authRequest = new AuthRequest("Bobby", "123");
	        
	        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
	                .thenThrow(new RuntimeException("invalid access"));

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.getToken(authRequest));
	        assertEquals("invalid access", exception.getMessage());
	        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
	    }

	    @Test
	    void testValidateToken_ShouldReturnValidMessage_WhenTokenIsValid() {
	      
	        String token = "validToken";
	        doNothing().when(authService).validateToken(token);

	        String result = authController.validateToken(token);

	        assertEquals("Token is valid", result);
	        verify(authService, times(1)).validateToken(token);
	    }

	    @Test
	    void testValidateToken_ShouldThrowException_WhenTokenIsInvalid() {
	      
	        String token = "invalidToken";
	        doThrow(new RuntimeException("Invalid token"))
	                .when(authService).validateToken(token);

	        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.validateToken(token));
	        assertEquals("Invalid token", exception.getMessage());
	        verify(authService, times(1)).validateToken(token);
	    }

}
