package com.host.SpringBootGraalVMServer.exceptions;

public class UserNotUpdatedException extends RuntimeException {
    public UserNotUpdatedException(String message){
        super(message);
    }
}
