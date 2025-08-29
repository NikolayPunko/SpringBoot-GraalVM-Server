package com.savushkin.Edi.controller;

import com.savushkin.Edi.dto.*;
import com.savushkin.Edi.exceptions.UserNotFoundException;
import com.savushkin.Edi.model.User;
import com.savushkin.Edi.security.JWTUtil;
import com.savushkin.Edi.service.RegistrationService;
import com.savushkin.Edi.service.UserDetailsService;
import com.savushkin.Edi.service.UserService;
import com.savushkin.Edi.util.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserValidator userValidator, RegistrationService registrationService, JWTUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDTO> performAuthentication(@RequestBody LoginRequestDTO loginRequestDto) {
        log.info("Authenticate req: {}", loginRequestDto.getUsername());

        Optional<User> optionalUser = userService.findByUsername(loginRequestDto.getUsername());
        int tokenTime = 60*24; // Изначально сутки
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getLifeTime() != null){
                tokenTime = user.getLifeTime();
            }
        } else {
            throw new UserNotFoundException();
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                        loginRequestDto.getPassword());

        authenticationManager.authenticate(authToken);

        String token = jwtUtil.generateToken(loginRequestDto.getUsername(), tokenTime);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @PostMapping("/encrypt-password")
    public ResponseEntity<RegistrationRespDTO> performRegistration(@RequestBody RegistrationReqDTO requestDTO) {
        log.info("Encrypt password req.");
        String encodedPassword = registrationService.encryptPassword(requestDTO.getPassword());

        return ResponseEntity.ok(new RegistrationRespDTO(encodedPassword));
    }

    private User convertToPerson(UserDTO userDTO) {
        return new ModelMapper().map(userDTO, User.class);
    }


}
