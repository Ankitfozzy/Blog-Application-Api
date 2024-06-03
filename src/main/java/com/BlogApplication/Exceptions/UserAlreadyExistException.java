package com.BlogApplication.Exceptions;

@SuppressWarnings("serial")
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
