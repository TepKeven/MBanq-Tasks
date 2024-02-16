package com.tepkeven.bankservices.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpMessageResponse {
    
    private int status;
    private String message;
    private List<ErrorModel> fieldErrors;

    public HttpMessageResponse(int status, String message, List<ErrorModel> fieldErrors){
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
