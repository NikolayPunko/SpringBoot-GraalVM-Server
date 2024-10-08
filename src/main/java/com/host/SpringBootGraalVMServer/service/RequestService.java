package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.security.UserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final UserDetailsService userDetailsService;

    @Autowired
    public RequestService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public void checkAccess(String partOfUrl){
        UserDetails userDetails = userDetailsService.getUserDetails();
        List<GrantedAuthority> authorities = userDetails.getAuthorities();
        System.out.println(authorities);
        if(!authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            authorities.retainAll(DirectoryService.NS_SRVFORM_MAP.get(partOfUrl).getRoleList());
            if(authorities.isEmpty()){
                throw new AccessDeniedException(String.format("Недостаточно прав для обращения по адресу %s", partOfUrl));
            }
        }
    }

    public String getPartOfUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        return requestURI.substring(contextPath.length());
    }





}
