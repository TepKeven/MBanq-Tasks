package com.tepkeven.bankservices.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorModel {
    
    private String code;
    private String message;
    private String field;
}
