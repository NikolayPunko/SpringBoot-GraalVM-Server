package com.host.SpringBootGraalVMServer.exceptions;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message){
        super(message);
    }
}
