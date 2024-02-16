package com.tepkeven.bankservices.service.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tepkeven.bankservices.dto.TxnRequestDto;
import com.tepkeven.bankservices.dto.TxnResponseDto;
import com.tepkeven.bankservices.entity.Account;
import com.tepkeven.bankservices.entity.CustomUserDetail;
import com.tepkeven.bankservices.entity.Transaction;
import com.tepkeven.bankservices.repository.AccountRepository;
import com.tepkeven.bankservices.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class FTransactionService {
    
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    public FTransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository ){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    private TxnResponseDto TxnToDto(String accountNo, Transaction txn){
        return new TxnResponseDto(txn.getBatchId(), txn.getTxnDate(), txn.getAmount(), accountNo);
    }

    public TxnResponseDto deposit(TxnRequestDto txnDto) throws Exception{
        
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Get current authenticated user
        Transaction txn = new Transaction();
        Account account = accountRepository.findByAccountNo(txnDto.accountNo()).orElse(null);
        
        if(account == null){ 
            throw new Exception("Account does not exist");
        }

        if(account.getUser().getId() != userDetail.getUserId()){
            throw new Exception("This account does not belong to your user account");
        }

        if(txnDto.amount() < 1){
            throw new Exception("Minimum Deposit Amount is 1$");
        }
        
        if(account.getBlockDate() != null){
            throw new Exception("Account No " + txnDto.accountNo() + " is Blocked");
        }

        txn.setAmount(txnDto.amount());
        txn.setBatchId("" + (long) (Math.random() * 9999999999L));
        txn.setTxnCode(1);
        txn.setAccount(account);

        account.setBalance(account.getBalance() + txnDto.amount());

        accountRepository.save(account);
        Transaction depositTxn = transactionRepository.save(txn);
        
        return TxnToDto(txnDto.accountNo(), depositTxn);

    }

    public TxnResponseDto withdraw(TxnRequestDto txnDto) throws Exception{
        
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Get current authenticated user
        Transaction txn = new Transaction();
        Account account = accountRepository.findByAccountNo(txnDto.accountNo()).orElse(null);

        if(account == null){
            throw new Exception("Account does not exist");
        }

        if(account.getUser().getId() != userDetail.getUserId()){
            throw new Exception("This account does not belong to your user account");
        }

        if(txnDto.amount() < 1){
            throw new Exception("Minimum Withdrawl Amount is 1$");
        }

        if(account.getBalance() < txnDto.amount()){
            throw new Exception("Insufficient Balance for Withdrawl");
        }
        
        if(account.getBlockDate() != null){
            throw new Exception("Account No " + txnDto.accountNo() + " is Blocked");
        }
        
        txn.setAmount(txnDto.amount() * -1);
        txn.setBatchId("" + (long) (Math.random() * 9999999999L));
        txn.setTxnCode(2);
        txn.setAccount(account);

        account.setBalance(account.getBalance() - txnDto.amount());

        accountRepository.save(account);
        Transaction withdrawTxn = transactionRepository.save(txn);
        
        return TxnToDto(txnDto.accountNo(), withdrawTxn);

    }
}
