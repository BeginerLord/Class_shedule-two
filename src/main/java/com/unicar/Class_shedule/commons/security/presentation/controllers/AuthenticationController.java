package com.unicar.Class_shedule.commons.security.presentation.controllers;

import com.unicar.Class_shedule.commons.utils.constants.EndpointsConstants;
import com.unicar.Class_shedule.commons.security.presentation.dtos.AuthCreateUserRequest;
import com.unicar.Class_shedule.commons.security.presentation.dtos.AuthLoginRequest;
import com.unicar.Class_shedule.commons.security.presentation.dtos.AuthResponse;
import com.unicar.Class_shedule.commons.security.service.CustomsDetailServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthenticationController {

    @Autowired
    private CustomsDetailServices userDetailService;

    @PostMapping(EndpointsConstants.ENDPOINT_SIGNUP)
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping(EndpointsConstants.ENDPOINT_LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

}