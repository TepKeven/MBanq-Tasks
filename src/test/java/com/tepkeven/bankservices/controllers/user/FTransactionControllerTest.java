package com.tepkeven.bankservices.controllers.user;

import static org.mockito.Mockito.when;

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
import com.tepkeven.bankservices.controller.user.FTransactionController;
import com.tepkeven.bankservices.dto.TxnRequestDto;
import com.tepkeven.bankservices.dto.TxnResponseDto;
import com.tepkeven.bankservices.service.user.FTransactionService;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = FTransactionController.class)
public class FTransactionControllerTest {
    
    @MockBean
    private FTransactionService fTransactionService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TxnRequestDto txnRequest;
    private TxnResponseDto txnDepositResponse;
    private TxnResponseDto txnWithdrawResponse;
    private TxnRequestDto txnRequestFailed;

    @BeforeEach
    public void initialization(){

        txnRequest = new TxnRequestDto("11111111111", 10.00);
        txnDepositResponse = new TxnResponseDto("123456788", new Date(), 10.00, "11111111111");
        txnWithdrawResponse = new TxnResponseDto("123456788", new Date(), -10.00, "11111111111");
        txnRequestFailed = new TxnRequestDto("", null);
    }

    @Test
    @WithAnonymousUser // Anonymous users do not have permissions
    public void FTransactionController_AnonymousUserDeposit_ReturnUnauthorized() throws Exception{

        when(fTransactionService.deposit(txnRequest)).thenReturn(txnDepositResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/deposit")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(txnRequest)));
        
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void FTransactionController_UserDeposit_ReturnArgumentNotValidException() throws Exception{

        when(fTransactionService.deposit(txnRequestFailed)).thenReturn(txnDepositResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/deposit")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(txnRequestFailed)));

        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void FTransactionController_UserWithdraw_ReturnArgumentNotValidException() throws Exception{

        when(fTransactionService.withdraw(txnRequestFailed)).thenReturn(txnWithdrawResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/withdraw")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(txnRequestFailed)));

        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void FTransactionController_UserDeposit_ReturnTxnResponseDto() throws Exception{

        when(fTransactionService.deposit(txnRequest)).thenReturn(txnDepositResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/deposit")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(txnRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.batchId", CoreMatchers.is(txnDepositResponse.batchId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.amount", CoreMatchers.is(txnRequest.amount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accountNo", CoreMatchers.is(txnRequest.accountNo())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void FTransactionController_UserWithdraw_ReturnTxnResponseDto() throws Exception{

        when(fTransactionService.withdraw(txnRequest)).thenReturn(txnWithdrawResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/withdraw")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(txnRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.batchId", CoreMatchers.is(txnWithdrawResponse.batchId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.amount", CoreMatchers.is(txnRequest.amount() * -1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accountNo", CoreMatchers.is(txnRequest.accountNo())));
    }
}
