package com.savushkin.Edi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class PyCreateScriptDTO {

    private String filename;
    private MultipartFile file;

}
