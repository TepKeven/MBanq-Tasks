package com.tepkeven.bankservices.dto;

import java.util.Date;

public record TxnResponseDto(

    String batchId,
    Date txnDate,
    Double amount,
    String accountNo

) {}
