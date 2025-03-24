package com.training.IdentityService.controller;

import com.training.IdentityService.dto.AuthRequest;
import com.training.IdentityService.entity.Role;
import com.training.IdentityService.entity.UserCredential;
import com.training.IdentityService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register/user")
    public String addNewUser(@RequestBody UserCredential user){
        user.setRole(Role.USER);
        return service.saveUser(user);
    }


    @PostMapping("/register/admin")
    public String addNewAdmin(@RequestBody UserCredential user){
        user.setRole(Role.ADMIN);
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return service.generateToken(authRequest.getUsername());
        }
        else {
            throw new RuntimeException("invalid Exception");
        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        service.validateToken(token);
        return "Token is valid";
    }

}
