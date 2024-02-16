package com.tepkeven.bankservices.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpItemResponse<T> {
    
    private int status;
    private String message;
    private T result;

    public HttpItemResponse(int status, String message, T result){
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
