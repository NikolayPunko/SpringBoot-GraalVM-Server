package com.savushkin.Edi.exceptions;

import lombok.Getter;

@Getter
public class PyScriptException extends RuntimeException {
    private String output;

    public PyScriptException(String message) {
        super(message);
    }

    public PyScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public PyScriptException(String message, String output) {
        super(message);
        this.output = output;
    }

    public PyScriptException(String message, String output, Throwable cause) {
        super(message, cause);
        this.output = output;
    }

}
