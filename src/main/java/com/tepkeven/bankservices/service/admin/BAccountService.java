package com.tepkeven.bankservices.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tepkeven.bankservices.dto.AccountRequestDto;
import com.tepkeven.bankservices.dto.AccountResponseDto;
import com.tepkeven.bankservices.entity.Account;
import com.tepkeven.bankservices.entity.User;
import com.tepkeven.bankservices.repository.AccountRepository;
import com.tepkeven.bankservices.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BAccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public BAccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountResponseDto AccountToDto(Account account){
        return new AccountResponseDto(account.getId(), account.getAccountNo(), account.getAccType(), account.getOpenDate(), account.getBalance(), account.getAccStatus(), account.getTransactions());
    }

    public List<AccountResponseDto> getAccounts() throws Exception {
        return accountRepository.findAll().stream().map(this::AccountToDto).collect(Collectors.toList());
    } 

    public AccountResponseDto getAccountByAccountNo(String accountNo) throws Exception{
        
        Account account = accountRepository.findByAccountNo(accountNo).orElse(null);

        if(account == null){
            throw new Exception("Account does not exist");
        }

        return AccountToDto(account);
    }

    public AccountResponseDto createAccount(AccountRequestDto accountDto) throws Exception {
        
        User existedUser = userRepository.findById(accountDto.userId()).orElse(null);

        if(existedUser == null){
            throw new Exception("User does not exist");
        }

        if(accountDto.userId() == null || accountDto.userId() <= 0){
            throw new Exception("User id cannot be empty");
        }

        String accountNo = "" + (long) (Math.random() * 99999999999999L);
        
        Account account = new Account();
        account.setAccountNo(accountNo);
        account.setAccType(accountDto.accType());
        account.setBalance(accountDto.amount());
        
        User user = new User();
        user.setId(accountDto.userId());
        account.setUser(user);

        Account createdAccount = accountRepository.save(account);
        return AccountToDto(createdAccount);
    }

    public AccountResponseDto editAccountByAccountNo(String accountNo, AccountRequestDto accountDto) throws Exception{

        User existedUser = userRepository.findById(accountDto.userId()).orElse(null);

        if(existedUser == null){
            throw new Exception("User does not exist");
        }

        if(accountDto.userId() == null || accountDto.userId() <= 0){
            throw new Exception("User id cannot be empty");
        }
        
        Account account = accountRepository.findByAccountNo(accountNo).get();
        account.setAccType(accountDto.accType());
        account.setBalance(accountDto.amount());

        User user = new User();
        user.setId(accountDto.userId());
        account.setUser(user);

        Account editedAccount = accountRepository.save(account);
        return AccountToDto(editedAccount);
    }

    @Transactional
    public void deleteAccountByAccountNo(String accountNo) throws Exception {
        accountRepository.deleteByAccountNo(accountNo);
    }
}
