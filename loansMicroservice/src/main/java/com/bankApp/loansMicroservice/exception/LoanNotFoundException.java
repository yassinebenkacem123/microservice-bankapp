package com.bankApp.loansMicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoanNotFoundException extends RuntimeException{

    public  LoanNotFoundException(String ResourceName, String fieldName, String fieldValue){
        super(String.format("%s not found with %s : '%s'", ResourceName, fieldName, fieldValue));
    }
}
