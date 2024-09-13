package com.host.SpringBootGraalVMServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScriptPayload {

    String request;
    String response;
    String connection;

}
