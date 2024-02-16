package com.tepkeven.bankservices.controllers.admin;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tepkeven.bankservices.config.SecurityConfig;
import com.tepkeven.bankservices.controller.admin.BUserController;
import com.tepkeven.bankservices.dto.UserRequestDto;
import com.tepkeven.bankservices.dto.UserResponseDto;
import com.tepkeven.bankservices.entity.Account;
import com.tepkeven.bankservices.service.admin.BUserService;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = BUserController.class)
public class BUserControllerTest {
    
    @MockBean
    private BUserService bUserService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<UserResponseDto> users = new ArrayList<UserResponseDto>();
    private UserResponseDto userDto1;
    private UserResponseDto userDto2;
    private UserResponseDto userDto3;
    private UserRequestDto userCreateRequest;
    private UserRequestDto userEditRequest;
    private UserResponseDto userEditResponse;

    @BeforeEach
    public void initialization() throws Exception{

        userDto1 = new UserResponseDto("Keven", "Tep", "Male", new ArrayList<Account>());
        userDto2 = new UserResponseDto("John", "Doe", "Male", new ArrayList<Account>());
        userDto3 = new UserResponseDto("Jenny", "Wilson", "Female", new ArrayList<Account>());
        
        userCreateRequest = new UserRequestDto("John", "Doe", new SimpleDateFormat("yyyy-MM-dd").parse("2001-07-24"), "Male", "01234567891", "helloworld", "Cambodia", 2L);
        userEditRequest = new UserRequestDto("Mike", "Thomson", new SimpleDateFormat("yyyy-MM-dd").parse("2001-07-24"), "Male", "01234567891", "helloworld", "Cambodia", 1L);
        userEditResponse = new UserResponseDto("Mike", "Thomson", "Male", new ArrayList<Account>());

        users.add(userDto1);
        users.add(userDto2);
        users.add(userDto3);
    }

    @Test
    @WithMockUser(roles = "USER") // Normal authenticated users do not have enough permissions
    public void BUserController_UserGetUsers_ReturnForbidden() throws Exception{

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users")
                                        .contentType(MediaType.APPLICATION_JSON));
                                        
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithAnonymousUser // Anonymous users do not have permissions
    public void BUserController_AnonymousUserGetUsers_ReturnUnauthorized() throws Exception{

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users")
                                        .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BUserController_AdminGetUsers_ReturnListUserResponseDto() throws Exception{

        when(bUserService.getUsers()).thenReturn(users);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users")
                                        .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.size()", CoreMatchers.is(users.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].firstname", CoreMatchers.is(users.get(0).firstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].gender", CoreMatchers.is(users.get(0).gender())));
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BUserController_AdminGetUser_ReturnUserResponseDto() throws Exception{

        Long userId = 1L;

        when(bUserService.getUser(userId)).thenReturn(userDto1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/" +  userId)
                                        .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.firstname", CoreMatchers.is(userDto1.firstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.gender", CoreMatchers.is(userDto1.gender())));
                
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BUserController_AdminCreateUser_ReturnUserResponseDto() throws Exception{

        when(bUserService.addUser(userCreateRequest)).thenReturn(userDto2);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/users/create")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(userCreateRequest)));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.firstname", CoreMatchers.is(userDto2.firstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.gender", CoreMatchers.is(userDto2.gender())));
                
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BUserController_AdminEditUser_ReturnUserResponseDto() throws Exception{

        Long userId = 1L; // Simulate edit userDto2 using userEditRequest and return userEditResponse

        when(bUserService.editUser(userId, userEditRequest)).thenReturn(userEditResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/users/edit/" + userId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(userEditRequest)));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.firstname", CoreMatchers.is(userEditRequest.firstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.gender", CoreMatchers.is(userCreateRequest.gender())));
                
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BUserController_AdminDeleteUser_ReturnSuccess() throws Exception{

        Long userId = 1L;

        doNothing().when(bUserService).deleteUser(userId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/users/delete/" + userId)
                                        .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")));                
    }
}

