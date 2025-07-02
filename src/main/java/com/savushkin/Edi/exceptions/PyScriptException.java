package com.savushkin.Edi.exceptions;

public class PyScriptException extends RuntimeException {
    public PyScriptException(String message) {
        super(message);
    }

    public PyScriptException(String message, Throwable cause) {
        super(message, cause);
    }

}
