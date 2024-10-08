package com.Hospital.Hospital.Management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.Hospital.Management.Model.AuthenticationRequest;
import com.Hospital.Hospital.Management.Model.RegisterRequest;
import com.Hospital.Hospital.Management.Service.Authenticate.AuthService;
import com.Hospital.Hospital.Management.Service.Authenticate.AuthenticationResponse;
import com.Hospital.Hospital.Management.Service.Authenticate.TokenBlacklistService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    // to register the user
    @PostMapping("/Register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authResponse = authService.register(registerRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    // to user login
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        logger.info("Received Authentication request for email: {}", request.getEmail());
        AuthenticationResponse response = authService.authenticate(request);
        logger.info("Authentication done successfully for email: {}", request.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getOtp/{email}")
    public ResponseEntity<String> getOtp(@PathVariable("email") String email){
        String response = authService.getOtp(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<Boolean> verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp ){
        Boolean response = authService.varifyOtp(email, otp);
        if (response) {
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Boolean> updatePassword(@RequestParam("email") String email, @RequestParam("password") String newPassword){
        Boolean response = authService.updatePassword(email,newPassword);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // to user logout
    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            tokenBlacklistService.blacklistToken(token);
            return "Logged out successfully";
        }
        return "Invalid token";
    }

}