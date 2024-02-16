package com.tepkeven.bankservices.controller.user;

import org.springframework.web.bind.annotation.RestController;

import com.tepkeven.bankservices.dto.TxnRequestDto;
import com.tepkeven.bankservices.dto.TxnResponseDto;
import com.tepkeven.bankservices.model.HttpItemResponse;
import com.tepkeven.bankservices.service.user.FTransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction API for User")
public class FTransactionController {

    private final FTransactionService frontTransactionService;

    public FTransactionController(FTransactionService frontTransactionService){
        this.frontTransactionService = frontTransactionService;
    }
    
    @PostMapping("deposit")
    public ResponseEntity<HttpItemResponse<TxnResponseDto>> depositMoney(@Valid @RequestBody TxnRequestDto txnDto) throws Exception {
        
        HttpItemResponse<TxnResponseDto> responseResult = new HttpItemResponse<TxnResponseDto>(200, "Success", this.frontTransactionService.deposit(txnDto)); 
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }

    @PostMapping("withdraw")
    public ResponseEntity<HttpItemResponse<TxnResponseDto>> withdrawMoney(@Valid @RequestBody TxnRequestDto txnDto) throws Exception {
        
        HttpItemResponse<TxnResponseDto> responseResult = new HttpItemResponse<TxnResponseDto>(200, "Success", this.frontTransactionService.withdraw(txnDto)); 
        return new ResponseEntity<>(responseResult, HttpStatus.OK);
    }
    
}
