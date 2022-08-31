package com.github.spencerk.ReimbursementAPI.security;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.repository.EmployeeRepository;
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

    private final EmployeeRepository employeeRepo;

    @Autowired
    public UserDetailsService(EmployeeRepository dao) {
        employeeRepo = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user;
        Employee employee;
        List<GrantedAuthority> roles = new ArrayList<>();

        if(employeeRepo.findByUsername(username).isEmpty()) throw new UsernameNotFoundException("User not found");

        employee = employeeRepo.findByUsername(username).get();
        roles.add(new SimpleGrantedAuthority(employee.getRole().toString()));
        user = User.withUsername(employee.getUsername())
                .password(employee.getPassword())
                .authorities(roles)
                .build();

        return user;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        final int    strength    = 10;
        SecureRandom salt        = new SecureRandom();

        return new BCryptPasswordEncoder(strength, salt);
    }
}
