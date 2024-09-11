package com.host.SpringBootGraalVMServer.service;

import com.host.SpringBootGraalVMServer.exceptions.UserNotFoundException;
import com.host.SpringBootGraalVMServer.model.User;
import com.host.SpringBootGraalVMServer.repositories.UsersRepository;
import com.host.SpringBootGraalVMServer.security.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public PersonDetailsService(UsersRepository usersRepository) {
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
}
