package com.github.spencerk.ReimbursementAPI.security;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final Logger                logger;
    private final EmployeeRepository    employeeRepo;

    @Autowired
    public UserDetailsService(EmployeeRepository repo) {
        employeeRepo = repo;
        logger = LoggerFactory.getLogger(UserDetailsService.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user;
        Employee employee;
        List<GrantedAuthority> roles = new ArrayList<>();

        logger.trace("UserDetailsService.loadUserByUsername(username)");

        if(employeeRepo.findByUsername(username).isEmpty()) {
            logger.error("Unable to find user by username from DB");

            throw new UsernameNotFoundException("User not found");
        }

        employee = employeeRepo.findByUsername(username).get();
        roles.add(new SimpleGrantedAuthority(employee.getRole().toString()));
        user = User.withUsername(employee.getUsername())
                .password(employee.getPassword())
                .authorities(roles)
                .build();

        logger.info("Returning UserDetails object");

        return user;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        final int    strength    = 10;
        SecureRandom salt        = new SecureRandom();

        return new BCryptPasswordEncoder(strength, salt);
    }
}
