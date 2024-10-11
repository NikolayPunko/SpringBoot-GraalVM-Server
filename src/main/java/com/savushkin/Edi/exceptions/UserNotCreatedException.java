package com.savushkin.Edi.exceptions;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message){
        super(message);
    }
}
