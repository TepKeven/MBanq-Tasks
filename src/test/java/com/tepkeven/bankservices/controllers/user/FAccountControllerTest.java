package com.tepkeven.bankservices.controllers.user;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

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
import com.tepkeven.bankservices.controller.user.FAccountController;
import com.tepkeven.bankservices.dto.AccountRequestDto;
import com.tepkeven.bankservices.dto.AccountResponseDto;
import com.tepkeven.bankservices.entity.Transaction;
import com.tepkeven.bankservices.service.user.FAccountService;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = FAccountController.class)
public class FAccountControllerTest {
    
    @MockBean
    private FAccountService fAccountService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountRequestDto accountRequest;
    private AccountResponseDto accountDto;
    private AccountRequestDto accountRequestFailed;

    @BeforeEach
    public void initialization(){

        accountRequest = new AccountRequestDto("VISA", 20.00, 1L);
        accountDto = new AccountResponseDto(1L, "11111111111", "VISA", new Date(), 20.00, 1, new ArrayList<Transaction>());
        accountRequestFailed = new AccountRequestDto("", 0.00, null);
    }

    @Test
    @WithAnonymousUser // Anonymous users do not have permissions
    public void FAccountController_AnonymousUserGetAccount_ReturnUnauthorized() throws Exception{

        String accountNo = accountDto.accountNo();

        when(fAccountService.getAccountByAccountNo(accountNo)).thenReturn(accountDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/" + accountNo)
            .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER") 
    public void FAccountController_UserCreateAccount_ReturnArgumentNotValidException() throws Exception{

        when(fAccountService.createAccount(accountRequestFailed)).thenReturn(accountDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/create")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(accountRequestFailed)));
        
        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "USER") 
    public void FAccountController_UserCreateAccount_ReturnAccountResponseDto() throws Exception{

        when(fAccountService.createAccount(accountRequest)).thenReturn(accountDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts/create")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(accountRequest)));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accountNo", CoreMatchers.is(accountDto.accountNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.balance", CoreMatchers.is(accountRequest.amount())));
    }

    @Test
    @WithMockUser(roles = "USER") 
    public void FAccountController_UserGetAccount_ReturnAccountResponseDto() throws Exception{

        String accountNo = accountDto.accountNo();

        when(fAccountService.getAccountByAccountNo(accountNo)).thenReturn(accountDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/" +  accountNo)
                                        .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accountNo", CoreMatchers.is(accountDto.accountNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.balance", CoreMatchers.is(accountDto.balance())));
    }
}
