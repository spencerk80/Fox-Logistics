package com.github.spencerk.ReimbursementAPI.controller;

import com.github.spencerk.ReimbursementAPI.entity.AuthenticationRequest;
import com.github.spencerk.ReimbursementAPI.entity.AuthenticationResponse;
import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.security.UserDetailsService;
import com.github.spencerk.ReimbursementAPI.service.EmployeeService;
import com.github.spencerk.ReimbursementAPI.service.JwtBlacklistService;
import com.github.spencerk.ReimbursementAPI.util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class AuthController {

    private final AuthenticationManager     authManager;
    private final UserDetailsService        userDetailsService;
    private final EmployeeService           employeeService;
    private final JWT                       JWT;
    private final JwtBlacklistService       jwtBlacklistService;

    @Autowired
    public AuthController(
            AuthenticationManager authManager, UserDetailsService userDetailsService,
            EmployeeService employeeService, JWT jwt, JwtBlacklistService jwtBlacklistService
    ) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.employeeService = employeeService;
        this.JWT = jwt;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @PostMapping("auth/login")
    public ResponseEntity<AuthenticationResponse> authUser(@RequestBody AuthenticationRequest authRequest) {
        UserDetails userDetails;
        Employee employee;
        AuthenticationResponse response = new AuthenticationResponse();

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch(BadCredentialsException e) {
            return ResponseEntity.status(401).body(null);
        }

        userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        employee = employeeService.getEmployeeByUsername(userDetails.getUsername());

        response.setJwt(JWT.createJWT(userDetails));
        response.setEmployee(employee);

        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/logout")
    public ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        jwtBlacklistService.blacklistJwt(jwt.substring(7));

        return ResponseEntity.ok().body("Bye-bye");
    }
}
