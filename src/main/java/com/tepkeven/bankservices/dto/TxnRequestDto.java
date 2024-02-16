package com.tepkeven.bankservices.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TxnRequestDto(
    
    @NotBlank(message = "Account number cannot be empty")
    String accountNo,

    @NotNull(message = "Amount cannot be empty")
    @Min(value = 1, message = "Amount must be at least 1$")
    Double amount
) {}
