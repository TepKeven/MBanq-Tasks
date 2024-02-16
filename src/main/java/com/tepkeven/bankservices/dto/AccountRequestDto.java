package com.tepkeven.bankservices.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDto(
    
    @NotBlank(message = "Account type cannot be empty")
    String accType,

    @NotNull(message = "Amount cannot be empty")
    @Min(value = 1, message = "Amount must be at least 1$")
    Double amount,

    @Min(value = 1, message = "User id cannot be empty")
    Long userId
) {}
