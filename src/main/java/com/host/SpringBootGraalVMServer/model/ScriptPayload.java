package com.host.SpringBootGraalVMServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.graalvm.polyglot.HostAccess;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ScriptPayload {
    @HostAccess.Export //требуется, чтобы сделать метод доступным из языка встраивания.
    String request;
    @HostAccess.Export //требуется, чтобы сделать метод доступным из языка встраивания.
    String response;
    @HostAccess.Export //требуется, чтобы сделать метод доступным из языка встраивания.
    String connection;

}
