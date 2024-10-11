package com.savushkin.Edi.exceptions;

public class UserNotUpdatedException extends RuntimeException {
    public UserNotUpdatedException(String message){
        super(message);
    }
}
