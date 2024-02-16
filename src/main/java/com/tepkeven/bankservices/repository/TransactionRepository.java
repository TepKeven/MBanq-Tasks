package com.tepkeven.bankservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tepkeven.bankservices.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
