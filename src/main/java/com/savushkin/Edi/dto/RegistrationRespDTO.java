package com.savushkin.Edi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRespDTO {

    private String encodedPassword;

    public RegistrationRespDTO(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}
