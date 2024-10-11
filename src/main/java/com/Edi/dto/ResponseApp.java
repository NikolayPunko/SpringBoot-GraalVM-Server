package com.Edi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseApp {

    private String status;
    private String error;

    public ResponseApp(String status, String error) {
        this.status = status;
        this.error = error;
    }
}
