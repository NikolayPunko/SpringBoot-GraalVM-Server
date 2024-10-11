package com.host.SpringBootGraalVMServer.model.directory;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "NS_WEBORG")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NsWebOrg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_ID")
    private int fId;

    @Column(name = "ORGNAME")
    private String orgName;

    @Column(name = "GLN")
    private String gln;

    @Column(name = "FULLNAME")
    private String fullName;

    public NsWebOrg trim(){
        setOrgName(getOrgName().trim());
        setGln(getGln().trim());
        setFullName(getFullName().trim());
        return this;
    }

}
