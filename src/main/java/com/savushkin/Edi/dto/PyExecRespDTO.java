package com.savushkin.Edi.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PyExecRespDTO {

    private boolean success;
    private String output;
    private String error;
}

