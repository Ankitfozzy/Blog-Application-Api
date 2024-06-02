package com.BlogApplication.Exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
