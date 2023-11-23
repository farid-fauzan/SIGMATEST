package com.sigma.rest.controller;


import com.sigma.rest.services.TokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenServices tokenServices;

    @PostMapping("/extract")
    public ResponseEntity extractToken(String token){

        ResponseEntity responseEntity = tokenServices.extractToken(token);
        return responseEntity;
    }
}
