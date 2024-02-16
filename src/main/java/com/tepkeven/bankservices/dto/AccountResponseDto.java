package com.tepkeven.bankservices.dto;

import java.util.Date;
import java.util.List;

import com.tepkeven.bankservices.entity.Transaction;

public record AccountResponseDto(
    Long accountId,
    String accountNo,
    String accType,
    Date openDate,
    Double balance,
    int accStatus,
    List<Transaction> transactions
) {}
