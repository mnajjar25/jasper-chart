package com.esense.jasperchart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmployeeAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public EmployeeAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public EmployeeAPIException(String message, HttpStatus status, String msg) {
        super(message);
        this.status = status;
        this.message = msg;
    }
}
