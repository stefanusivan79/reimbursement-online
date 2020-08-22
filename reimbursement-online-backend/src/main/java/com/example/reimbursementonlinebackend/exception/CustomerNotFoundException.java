package com.example.reimbursementonlinebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException() {
        super("Customer not found!");
    }
}
