package com.savushkin.Edi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class NewScriptDTO {

    @JsonProperty("F_ID")
    private int fId;
    private String path;
    private String name;
    private String lang;
    private String type;
    private String script;

    @Override
    public String toString() {
        return "NewScriptDTO{" +
                "type='" + type + '\'' +
                ", lang='" + lang + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", fId=" + fId +
                '}';
    }
}
