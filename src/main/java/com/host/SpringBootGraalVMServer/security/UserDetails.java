package com.host.SpringBootGraalVMServer.security;

import com.host.SpringBootGraalVMServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final User user;

    @Autowired
    public UserDetails(User user) {
        this.user = user;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        String prefix = "ROLE_";
        List<GrantedAuthority> result = new ArrayList<>();
        for (String role : user.getRole().trim().split(";")) {
            result.add(new SimpleGrantedAuthority(prefix+role.toUpperCase()));
        }
        return result;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword().trim();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername().trim();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getPerson(){
        user.trim();
        return this.user;
    }
}
