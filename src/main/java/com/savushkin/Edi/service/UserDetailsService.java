package com.savushkin.Edi.service;

import com.savushkin.Edi.exceptions.UserNotFoundException;
import com.savushkin.Edi.model.User;
import com.savushkin.Edi.repositories.UsersRepository;
import com.savushkin.Edi.security.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> person = usersRepository.findByUsername(username);

        if (person.isEmpty()){
//            throw new UsernameNotFoundException("Username not found!");
            throw new UserNotFoundException();
        }

        return new UserDetails(person.get());
    }

    public UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }


}
