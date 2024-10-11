package com.savushkin.Edi.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "NS_WEBUSR")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_ID")
    private int F_ID;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIO")
    private String fullName;

    @Column(name = "ROLENAME")
    private String role;

    @Column(name = "ORGNAME")
    private String orgName;

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "F_ID=" + F_ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }

    public void trim(){
        setUsername(getUsername().trim());
        setPassword(getPassword().trim());
        setFullName(getFullName().trim());
        setRole(getRole().trim());
        setOrgName(getOrgName().trim());
    }
}
