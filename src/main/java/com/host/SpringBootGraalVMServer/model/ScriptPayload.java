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
    @HostAccess.Export //требуется, чтобы сделать метод доступным из языка встраивания.
    String request;
    @HostAccess.Export //требуется, чтобы сделать метод доступным из языка встраивания.
    String response;
    @HostAccess.Export //требуется, чтобы сделать метод доступным из языка встраивания.
    public Connection connection;

}
