package com.tepkeven.bankservices.controller.user;

import org.springframework.web.bind.annotation.RestController;

import com.tepkeven.bankservices.dto.AccountRequestDto;
import com.tepkeven.bankservices.dto.AccountResponseDto;
import com.tepkeven.bankservices.model.HttpItemResponse;
import com.tepkeven.bankservices.service.user.FAccountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account API for User")
public class FAccountController {

    private FAccountService fronAccountService;

    public FAccountController(FAccountService fronAccountService){
        this.fronAccountService = fronAccountService;
    }
    
    @PostMapping("create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequestDto account) throws Exception {
        
        HttpItemResponse<AccountResponseDto> response = new HttpItemResponse<AccountResponseDto>(200, "Success", this.fronAccountService.createAccount(account));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{accountNo}")  // Balance + Transaction History 
    public ResponseEntity<HttpItemResponse<AccountResponseDto>> GetAccountInfo(@PathVariable("accountNo") String accountNo) throws Exception {
        
        HttpItemResponse<AccountResponseDto> responseResult = new HttpItemResponse<AccountResponseDto>(200, "Success", this.fronAccountService.getAccountByAccountNo(accountNo));
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
    
}
