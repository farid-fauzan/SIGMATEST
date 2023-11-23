package com.sigma.rest.controller;

import com.sigma.rest.pojo.LoginPojo;
import com.sigma.rest.pojo.UserPojo;
import com.sigma.rest.services.AuthService;
import com.sigma.rest.services.UserServices;
import com.sigma.rest.utility.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<MessageModel> register(@Valid @RequestBody UserPojo dataUser){

        return userServices.register(dataUser);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity<MessageModel> validateUser(@RequestParam String email, @RequestParam String otp){

        return userServices.validate(email,otp);
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ResponseEntity<MessageModel> validateUser(@RequestParam String email){

        return userServices.status(email);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<MessageModel> login(@Valid @RequestBody LoginPojo dataLogin){
        return userServices.login(dataLogin);
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<MessageModel> requestResetPassword(@RequestParam String email) {
        return authService.resetPassword(email);
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<MessageModel> confirmResetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {

        return authService.confirmResetPassword(email, otp, newPassword);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageModel> logout(@RequestHeader("Authorization") String tokenHeader) {
        return userServices.logout(tokenHeader);
    }

    @PostMapping("/ubah-password/request")
    public ResponseEntity<MessageModel> requestUbahPassword(@RequestParam String email) {
        return authService.resetPassword(email);
    }

    @PostMapping("/ubah-password/confirm")
    public ResponseEntity<MessageModel> confirmUbahPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {

        return authService.confirmResetPassword(email, otp, newPassword);
    }

}
