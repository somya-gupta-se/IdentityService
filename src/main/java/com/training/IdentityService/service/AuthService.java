package com.training.IdentityService.service;

import com.training.IdentityService.entity.Role;
import com.training.IdentityService.entity.UserCredential;
import com.training.IdentityService.repository.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.logging.Logger;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;

    Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public String saveUser(UserCredential credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        if(credential.getRole().equals(Role.USER))
        {
            LOGGER.info("User saved successfully");
            return "User saved";
        }
        else{
            LOGGER.info("Admin saved successfully");
            return "Admin saved";
        }
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }


}
