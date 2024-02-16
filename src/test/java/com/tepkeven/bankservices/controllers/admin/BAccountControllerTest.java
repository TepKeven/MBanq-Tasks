package com.tepkeven.bankservices.controllers.admin;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tepkeven.bankservices.config.SecurityConfig;
import com.tepkeven.bankservices.controller.admin.BAccountController;
import com.tepkeven.bankservices.dto.AccountRequestDto;
import com.tepkeven.bankservices.dto.AccountResponseDto;
import com.tepkeven.bankservices.entity.Transaction;
import com.tepkeven.bankservices.service.admin.BAccountService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = BAccountController.class)
public class BAccountControllerTest {
    
    @MockBean
    private BAccountService bAccountService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<AccountResponseDto> accounts = new ArrayList<AccountResponseDto>();
    private AccountResponseDto accountDto1;
    private AccountResponseDto accountDto2;
    private AccountResponseDto accountDto3;
    private AccountRequestDto accountCreateRequest;
    private AccountRequestDto accountEditRequest;
    private AccountResponseDto accountEditResponse;


    @BeforeEach
    public void initialization(){

        accountDto1 = new AccountResponseDto(1L, "11111111111", "VISA", new Date(), 10.00, 1, new ArrayList<Transaction>());
        accountDto2 = new AccountResponseDto(2L, "22222222222", "VISA", new Date(), 20.00, 1, new ArrayList<Transaction>());
        accountDto3 = new AccountResponseDto(3L, "33333333333", "VISA", new Date(), 30.00, 1, new ArrayList<Transaction>());
        
        accountCreateRequest = new AccountRequestDto("VISA", 20.00, 18L);
        accountEditRequest = new AccountRequestDto("VISA", 50.00, 7L);
        accountEditResponse = new AccountResponseDto(2L, "22222222222", "VISA", new Date(), 50.00, 1, new ArrayList<Transaction>());

        accounts.add(accountDto1);
        accounts.add(accountDto2);
        accounts.add(accountDto3);
    }

    @Test
    @WithMockUser(roles = "USER") // Normal authenticated users do not have enough permissions
    public void BAccountController_UserGetAccounts_ReturnForbidden() throws Exception{

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/accounts")
                                        .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithAnonymousUser // Anonymous users do not have permissions
    public void BAccountController_AnonymousUserGetAccounts_ReturnUnauthorized() throws Exception{

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/accounts")
                                        .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BAccountController_AdminGetAccounts_ReturnListAccountResponseDto() throws Exception{

        when(bAccountService.getAccounts()).thenReturn(accounts);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/accounts")
                                        .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.size()", CoreMatchers.is(accounts.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].accountNo", CoreMatchers.is(accounts.get(0).accountNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].balance", CoreMatchers.is(accounts.get(0).balance())));
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BAccountController_AdminGetAccount_ReturnAccountResponseDto() throws Exception{

        String accountNo = accountDto1.accountNo();

        when(bAccountService.getAccountByAccountNo(accountNo)).thenReturn(accountDto1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/accounts/" +  accountNo)
                                        .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accountNo", CoreMatchers.is(accountDto1.accountNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.balance", CoreMatchers.is(accountDto1.balance())));
                
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BAccountController_AdminCreateAccount_ReturnAccountResponseDto() throws Exception{

        when(bAccountService.createAccount(accountCreateRequest)).thenReturn(accountDto2);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/accounts/create")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(accountCreateRequest)));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accountNo", CoreMatchers.is(accountDto2.accountNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.balance", CoreMatchers.is(accountCreateRequest.amount())));
                
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BAccountController_AdminEditAccount_ReturnAccountResponseDto() throws Exception{

        String accountNo = accountDto2.accountNo(); // Simulate edit accountDto2 using accountEditRequest and return accountEditResponse

        when(bAccountService.editAccountByAccountNo(accountNo, accountEditRequest)).thenReturn(accountEditResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/accounts/edit/" + accountNo)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(accountEditRequest)));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accountNo", CoreMatchers.is(accountDto2.accountNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.balance", CoreMatchers.is(accountEditRequest.amount())));
                
    }

    @Test
    @WithMockUser(username = "012330245", password = "helloworld", roles = "ADMIN")
    public void BAccountController_AdminDeleteAccount_ReturnSuccess() throws Exception{

        String accountNo = accountDto3.accountNo();

        doNothing().when(bAccountService).deleteAccountByAccountNo(accountNo);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/accounts/delete/" + accountNo)
                                        .contentType(MediaType.APPLICATION_JSON));
        
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")));                
    }
}
