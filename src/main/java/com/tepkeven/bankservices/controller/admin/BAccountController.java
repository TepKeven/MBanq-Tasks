package com.tepkeven.bankservices.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tepkeven.bankservices.dto.AccountRequestDto;
import com.tepkeven.bankservices.dto.AccountResponseDto;
import com.tepkeven.bankservices.dto.UserResponseDto;
import com.tepkeven.bankservices.model.HttpItemResponse;
import com.tepkeven.bankservices.model.HttpItemsResponse;
import com.tepkeven.bankservices.service.admin.BAccountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/accounts")
@Tag(name = "Accounts API for Admin")
public class BAccountController {
    
    private final BAccountService backAccountService;

    public BAccountController(BAccountService backAccountService){
        this.backAccountService = backAccountService;
    }

    @GetMapping("")
    public ResponseEntity<HttpItemsResponse<AccountResponseDto>> getAccounts() throws Exception {

        HttpItemsResponse<AccountResponseDto> responseResult = new HttpItemsResponse<AccountResponseDto>(200, "Success", this.backAccountService.getAccounts());
        return ResponseEntity.status(200).body(responseResult);
    }

    @GetMapping("{accountNo}")
    public ResponseEntity<HttpItemResponse<AccountResponseDto>> getAccountByAccountNo(@PathVariable("accountNo") String accountNo) throws Exception {

        HttpItemResponse<AccountResponseDto> responseResult = new HttpItemResponse<AccountResponseDto>(200, "Success", this.backAccountService.getAccountByAccountNo(accountNo));
        return ResponseEntity.status(200).body(responseResult);
    }

    @PostMapping("create")
    public ResponseEntity<HttpItemResponse<AccountResponseDto>> addUser(@Valid @RequestBody AccountRequestDto accountDto) throws Exception {

        HttpItemResponse<AccountResponseDto> responseResult = new HttpItemResponse<AccountResponseDto>(200, "Success", this.backAccountService.createAccount(accountDto));
        return ResponseEntity.status(200).body(responseResult);
    }

    @PutMapping("edit/{accountNo}")
    public ResponseEntity<HttpItemResponse<AccountResponseDto>> editAccountByAccountNo(@PathVariable("accountNo") String accountNo, @Valid @RequestBody AccountRequestDto accountDto) throws Exception {

        HttpItemResponse<AccountResponseDto> responseResult = new HttpItemResponse<AccountResponseDto>(200, "Success", this.backAccountService.editAccountByAccountNo(accountNo, accountDto));
        return ResponseEntity.status(200).body(responseResult);
    }

    @DeleteMapping("delete/{accountNo}")
    public ResponseEntity<HttpItemResponse<UserResponseDto>> deleteAccountByAccountNo(@PathVariable("accountNo") String accountNo) throws Exception {

        this.backAccountService.deleteAccountByAccountNo(accountNo);
        HttpItemResponse<UserResponseDto> responseResult = new HttpItemResponse<UserResponseDto>(200, "Success", null);
        return ResponseEntity.status(200).body(responseResult);
    }
}
