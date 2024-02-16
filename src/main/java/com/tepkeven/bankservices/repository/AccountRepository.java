package com.tepkeven.bankservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tepkeven.bankservices.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNo(String accountNo);
    void deleteByAccountNo(String accountNo);
}
