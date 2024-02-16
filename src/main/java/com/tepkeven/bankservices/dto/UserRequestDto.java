package com.tepkeven.bankservices.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

    @NotBlank(message = "Firstname cannot be empty")
    String firstname,

    @NotBlank(message =  "Lastname cannot be empty")
    String lastname,

    @NotNull(message =  "Date of birth cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dob,

    @NotBlank(message = "Gender cannot be empty")
    String gender,

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 9, max = 11, message = "Incorrect phone format")
    String phone,

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password,
    
    String address,

    @NotNull(message = "Role cannot be empty")
    Long roleId

) {}

