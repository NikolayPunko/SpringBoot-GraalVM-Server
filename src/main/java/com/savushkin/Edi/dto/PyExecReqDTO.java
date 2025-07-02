package com.savushkin.Edi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PyExecReqDTO {

    private String filename;
    private List<String> parameters;
}
