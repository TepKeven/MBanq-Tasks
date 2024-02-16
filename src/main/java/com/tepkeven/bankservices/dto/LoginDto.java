package com.tepkeven.bankservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 9, max = 11, message = "Incorrect phone format")
    String phone,

    @NotBlank(message =  "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password

) {}