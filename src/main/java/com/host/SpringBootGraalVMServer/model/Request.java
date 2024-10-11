package com.host.SpringBootGraalVMServer.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Request {

    private String body;

    private String gln;

}
