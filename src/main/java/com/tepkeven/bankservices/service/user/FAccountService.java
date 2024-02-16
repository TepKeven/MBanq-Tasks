package com.tepkeven.bankservices.service.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tepkeven.bankservices.dto.AccountRequestDto;
import com.tepkeven.bankservices.dto.AccountResponseDto;
import com.tepkeven.bankservices.entity.Account;
import com.tepkeven.bankservices.entity.CustomUserDetail;
import com.tepkeven.bankservices.entity.User;
import com.tepkeven.bankservices.repository.AccountRepository;

@Service
public class FAccountService {
    
    private final AccountRepository accountRepository;

    public FAccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public AccountResponseDto AccountToDto(Account account){
        return new AccountResponseDto(account.getId(), account.getAccountNo(), account.getAccType(), account.getOpenDate(), account.getBalance(), account.getAccStatus(), account.getTransactions());
    }

    public AccountResponseDto getAccountByAccountNo(String accountNo) throws Exception{
        
        Account account = accountRepository.findByAccountNo(accountNo).orElse(null);
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Get current authenticated user

        if(account == null){
            throw new Exception("Account does not exist");
        }

        if(account.getUser().getId() != userDetail.getUserId()){
            throw new Exception("This account does not belong to your user account");
        }

        return AccountToDto(account);
    }

    public AccountResponseDto createAccount(AccountRequestDto accountDto) throws Exception{
    
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Get current authenticated user
        Long userId = userDetail.getUserId();

        String accountNo = "" + (long) (Math.random() * 99999999999999L);        
        Account account = new Account();
        account.setAccountNo(accountNo);
        account.setAccType(accountDto.accType());
        account.setBalance(accountDto.amount());
        
        User user = new User();
        user.setId(userId);
        account.setUser(user);

        Account createdAccount = accountRepository.save(account);
        return AccountToDto(createdAccount);
    }
}
