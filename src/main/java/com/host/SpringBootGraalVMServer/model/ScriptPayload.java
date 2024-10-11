package com.host.SpringBootGraalVMServer.model;

import lombok.*;
import org.graalvm.polyglot.HostAccess;

import java.sql.Connection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScriptPayload {

    private String bodyReq;

    private String gln;

}
