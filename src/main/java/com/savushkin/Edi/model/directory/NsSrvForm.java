package com.savushkin.Edi.model.directory;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "ROLE")
    private String role;

    public List<GrantedAuthority> getRoleList(){
        String prefix = "ROLE_";
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : getRole().split(";")) {
            roles.add(new SimpleGrantedAuthority(prefix+role.toUpperCase()));
        }
        return roles;
    }

    public NsSrvForm trim(){
        setPath(getPath().trim());
        setName(getName().trim());
        setLang(getLang().trim());
        setExecute(getExecute().trim());
        setScript(getScript().trim());
        setForm(getForm().trim());
        setRole(getRole().trim());
        return this;
    }

    public String getRole() {
        if (role==null)
            return "";
        return role;
    }
}
