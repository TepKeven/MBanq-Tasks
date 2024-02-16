package com.tepkeven.bankservices.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tepkeven.bankservices.model.ErrorModel;
import org.springframework.validation.FieldError;
import com.tepkeven.bankservices.model.HttpMessageResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpMessageResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        
        List<ErrorModel> validateFieldErrors = new ArrayList<ErrorModel>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            
            ErrorModel validationErrorModel = ErrorModel
                .builder()
                .code(fieldError.getCode())
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
            validateFieldErrors.add(validationErrorModel);
        }

        HttpMessageResponse responseResult = new HttpMessageResponse(422, "Field Errors", validateFieldErrors);

        return responseResult;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpMessageResponse handleDataIntegrityViolationException(Exception ex) {

        HttpMessageResponse responseResult = new HttpMessageResponse(404, ex.getMessage(), null);

        return responseResult;
    }
}
