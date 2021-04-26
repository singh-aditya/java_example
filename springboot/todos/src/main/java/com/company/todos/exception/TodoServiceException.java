package com.company.todos.exception;

public class TodoServiceException extends RuntimeException{
    public TodoServiceException(String msg) {
        super(msg);
    }
}
