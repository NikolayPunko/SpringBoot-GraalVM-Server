package com.savushkin.Edi.model.directory;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        try {
            String prefix = "ROLE_";
            List<GrantedAuthority> roles = new ArrayList<>();
            for (String role : getRole().split(";")) {
                roles.add(new SimpleGrantedAuthority(prefix+role.toUpperCase()));
            }
            return roles;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Error getting role list");
        }

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
