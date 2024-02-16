package com.tepkeven.bankservices.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpItemsResponse<T> {
    
    private int status;
    private String message;
    private List<T> result;

    public HttpItemsResponse(int status, String message, List<T> result){
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
