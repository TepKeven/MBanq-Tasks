package com.tepkeven.bankservices.dto;

import com.tepkeven.bankservices.entity.Account;
import java.util.List;


public record UserResponseDto(

    String firstname,
    String lastname,
    String gender,
    List<Account> accounts 

) {}
