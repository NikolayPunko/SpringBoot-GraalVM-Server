package com.host.SpringBootGraalVMServer.util;

import com.host.SpringBootGraalVMServer.model.User;
import com.host.SpringBootGraalVMServer.security.UserDetails;
import com.host.SpringBootGraalVMServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.findByUsername(user.getUsername()).isPresent())
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует");
    }

}
