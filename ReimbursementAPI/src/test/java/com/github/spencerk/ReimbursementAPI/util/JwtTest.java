package com.github.spencerk.ReimbursementAPI.util;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.enums.EmployeeRole;
import com.github.spencerk.ReimbursementAPI.repository.EmployeeRepository;
import com.github.spencerk.ReimbursementAPI.security.UserDetailsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {JWT.class, UserDetailsService.class, EmployeeRepository.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTest {
    @MockBean
    private EmployeeRepository repo;
    @Autowired
    private UserDetailsService userDetailsService;
    private UserDetails userDetails;

    private final String username = "bsmith",
                            userRole = "[MANAGER]";
    private final JWT JWT;
    private String jwtStr;

    public JwtTest() {
        this.JWT = new JWT("9afw87d98afwhd98ahwfd98hafw98thw98hft398ahwf3t98hwf98thwaf98ht");
    }

    @BeforeAll
    public void mkJwtStr() {
        when(repo.findByUsername(username)).thenReturn(Optional.of(new Employee(
                "0c7475d1-23d7-46bf-8420-7b5fb55a4c1a", "bsmith", "Bob", "Smith",
                "b.smith@email.com", "555-555-5555",
                "$2a$10$BbsywiERqvPth9Ah15HSaOt2chErdYC9PVca0qfFlWBGouKyZRRNu", EmployeeRole.MANAGER
        )));
        userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null) throw new UsernameNotFoundException("Database data not pre-loaded");
        jwtStr = JWT.createJWT(userDetails);
    }

    @Test
    public void createJwt() {
        //Matches a JWT pattern
        assertTrue(jwtStr.matches("^[\\da-zA-z_-]+.\\.[\\da-zA-z_-]+\\.[\\da-zA-z_-]+$"));
    }

    @Test
    public void getUsername() {
        assertEquals(username, JWT.getUsername(jwtStr));
    }

    @Test
    public void getUserRole() {
        assertEquals(userRole, JWT.getUserRole(jwtStr));
    }

    @Test
    public void getExp() {
        LocalDateTime exp           = JWT.getJwtExpiration(jwtStr);
        LocalDateTime oneDayBefore  = LocalDateTime.now().minusDays(1);
        LocalDateTime oneDayAfter   = LocalDateTime.now().plusDays(2);

        assertTrue(exp.isAfter(oneDayBefore) && exp.isBefore(oneDayAfter));
    }

    @Test
    public void validateJWT() {
        char charToMod = jwtStr.charAt(40);
        //Replace the char at index 40 with the letter 'a', unless it happens to be that already, then 'b'
        String modifiedJwt = jwtStr.substring(0, 40) + (charToMod == 'a' ? 'b' : 'a') + jwtStr.substring(41);

        assertTrue(JWT.validateJwt(jwtStr, userDetails));
        assertFalse(JWT.validateJwt(modifiedJwt, userDetails));
    }
}
