package com.authentication.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.authentication.dto.RoleDto;
import com.authentication.entity.Role;
import com.authentication.service.IRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharedLibrary.constants.Constants;
import com.sharedLibrary.dto.ResponseDto;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private IRoleService iRoleService;
	
	  @Test
	    public void testCreateRole() throws Exception {

		  RoleDto roleDto = new RoleDto();
		  roleDto.setName("xyz");
		  roleDto.setStatus("active");
		  
	        doNothing().when(iRoleService).createRole(roleDto);

	        ResponseDto expectedResponse = new ResponseDto("201", "Created Successfully");

	        mockMvc.perform(post("/auth/role/create")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(roleDto))) 
	                .andExpect(status().isCreated())
	                .andExpect(jsonPath("$.statusCode").value(expectedResponse.getStatusCode())) 
	                .andExpect(jsonPath("$.statusMsg").value(expectedResponse.getStatusMsg())); 
	    }
	  
	  
	  @Test
	  public void testFetchRoleDetails() throws Exception {

		  int id = 1;
	      Role role = new Role();
	      role.setId(id);
	      role.setName("xyz");
	      role.setStatus("active");

	      when(iRoleService.fetchRole(id)).thenReturn(role);

	      mockMvc.perform(get("/auth/role/fetch")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("xyz"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("active"));
	  }
	  
	  @Test
	  public void testUpdateRoleDetails_Success() throws Exception {
	      
	      RoleDto roleDto = new RoleDto();
	      roleDto.setId(1);
	      roleDto.setName("xyz");
	      roleDto.setStatus("active");

	      when(iRoleService.updateRole(roleDto)).thenReturn(true);

	      mockMvc.perform(put("/auth/role/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(roleDto)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testUpdateRoleDetails_Failure() throws Exception {
	      
	      RoleDto roleDto = new RoleDto();
	      roleDto.setId(1);
	      roleDto.setName("xyz");
	      roleDto.setStatus("active");
	      
	      when(iRoleService.updateRole(roleDto)).thenReturn(false);

	      mockMvc.perform(put("/auth/role/update")
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(roleDto)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testDeleteRoleDetails_Success() throws Exception {
	      
	      int id = 1;
	      when(iRoleService.deleteRole(id)).thenReturn(true);

	      mockMvc.perform(delete("/auth/role/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_200))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_200));
	  }
	  
	  @Test
	  public void testDeleteRoleDetails_Failure() throws Exception {
	      
	      int id = 1;
	      when(iRoleService.deleteRole(id)).thenReturn(false);

	      mockMvc.perform(delete("/auth/role/delete")
	              .param("id", String.valueOf(id)))
	              .andExpect(status().isInternalServerError())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(Constants.STATUS_500))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value(Constants.MESSAGE_500));
	  }
	  
	  @Test
	  public void testGetAllRoles() throws Exception {
	      Role role1 = new Role();
	      role1.setId(1);
	      role1.setName("xyz");
	      role1.setStatus("active");

	      Role role2 = new Role();
	      role2.setId(2);
	      role2.setName("xyz");
	      role2.setStatus("active");

	      List<Role> RoleList = Arrays.asList(role1, role2);
	      when(iRoleService.fetchAllRoles()).thenReturn(RoleList);

	      mockMvc.perform(get("/auth/role/All"))
	              .andExpect(status().isOk())
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.statusMsg").value("success"))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
	              .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
	  }


	
}
