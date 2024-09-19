package com.host.SpringBootGraalVMServer.model.directory;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "NS_SRVFORM")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NsSrvForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_ID")
    private int fId;

    @Column(name = "PATH")
    private String path;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LANG")
    private String lang;

    @Column(name = "[EXECUTE]")
    private String execute;

    @Column(name = "SCRIPT")
    private String script;

    @Column(name = "FORM")
    private String form;


}
