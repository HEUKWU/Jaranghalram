package com.hanghaemini4.jaranghalram.exceptionHandler;

public class UserServiceException extends RuntimeException{
    public UserServiceException(String message) {
        super(message);
    }
}
