package com.authentication.service.impl;

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

import com.authentication.dto.RoleDto;
import com.authentication.entity.Role;
import com.authentication.repository.RoleRepository;
import com.sharedLibrary.exception.ResourceNotFoundException;
import com.sharedLibrary.mapper.ObjectEntityCheckutil;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {
	
	@Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;
    

    @Test
    void testCreateRole() {
    	
    	 RoleDto roleDto = new RoleDto();
    	 roleDto.setName("xyz");
		  roleDto.setStatus("active");
        
        
        Role role = new Role();
        
        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(roleDto, role))
                        .thenAnswer(invocation -> null);

            when(roleRepository.save(any(Role.class))).thenReturn(role);

            roleService.createRole(roleDto);

            verify(roleRepository, times(1)).save(any(Role.class));
            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(roleDto, role), times(1));
        }
    }
    
    @Test
    void testCreateRole_NullDto() {
       
        RoleDto roleDto = null;

        assertThrows(IllegalArgumentException.class, () -> roleService.createRole(roleDto));
        verify(roleRepository, never()).save(any(Role.class));
    }
    
    @Test
    void testFetchRole() {
     
        int id = 1; 

        Role role = new Role();
        role.setId(id);
        role.setName("xyz");
        role.setStatus("active");


       when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        Role result = roleService.fetchRole(id);

        assertNotNull(result); 
        assertEquals(id, result.getId());
        assertEquals("xyz", result.getName()); 
        assertEquals("active", result.getStatus()); 
        verify(roleRepository, times(1)).findById(id); 
    }


    @Test
    void testFetchRoleNotFound() {
        int id = 1;

        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.fetchRole(id));
        verify(roleRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateRole() {
        
    	RoleDto roleDto = new RoleDto();
    	roleDto.setId(1);
    	roleDto.setName("xyz");
		  roleDto.setStatus("active");

        Role existingRole = new Role();
        existingRole.setId(1); 
        existingRole.setName("xyz");
        existingRole.setStatus("active");

        when(roleRepository.findById(roleDto.getId())).thenReturn(Optional.of(existingRole));

        try (MockedStatic<ObjectEntityCheckutil> mockedStatic = mockStatic(ObjectEntityCheckutil.class)) {
            mockedStatic.when(() -> ObjectEntityCheckutil.copyNonNullProperties(roleDto, existingRole))
                        .thenAnswer(invocation -> null);

            when(roleRepository.save(existingRole)).thenReturn(existingRole);

            boolean result = roleService.updateRole(roleDto);

            assertTrue(result);
            verify(roleRepository, times(1)).findById(roleDto.getId());
            verify(roleRepository, times(1)).save(existingRole);

            mockedStatic.verify(() -> ObjectEntityCheckutil.copyNonNullProperties(roleDto, existingRole), times(1));
        }
    }


    @Test
    void testUpdateRoleNotFound() {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1);

        when(roleRepository.findById(roleDto.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.updateRole(roleDto));
        verify(roleRepository, times(1)).findById(roleDto.getId());
    }

    @Test
    void testDeleteRole() {
        int id = 1;
        Role role = new Role();
        role.setId(id);

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).deleteById(id);

        boolean result = roleService.deleteRole(id);

        assertTrue(result);
        verify(roleRepository, times(1)).findById(id);
        verify(roleRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRoleNotFound() {
        int id = 1;

        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.deleteRole(id));
        verify(roleRepository, times(1)).findById(id);
    }

    @Test
    void testFetchAllRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.add(new Role());

        when(roleRepository.findAll()).thenReturn(roleList);

        List<Role> result = roleService.fetchAllRoles();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(roleRepository, times(1)).findAll();
    }



}
