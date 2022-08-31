package com.github.spencerk.ReimbursementAPI.security;

import com.github.spencerk.ReimbursementAPI.service.JwtBlacklistService;
import com.github.spencerk.ReimbursementAPI.util.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService    userDetailsService;
    private final JWT                   JWT;
    private final JwtBlacklistService jwtBlacklistService;

    @Autowired
    public JwtFilter(UserDetailsService userDetailsService, JWT jwt, JwtBlacklistService jwtBlacklistService) {
        this.userDetailsService = userDetailsService;
        this.JWT = jwt;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String    authorizationHeader    = request.getHeader("Authorization");
        String          jwt                     = null,
                        email                   = null;

        //Do NOT try to authenticate these endpoints. Causes 403 if tried
        if(
                "/api/auth/login".matches(request.getRequestURI())
                || "/api/employees/register".matches(request.getRequestURI())
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = JWT.getUsername(jwt);
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if(JWT.validateJwt(jwt, userDetails) && ! jwtBlacklistService.jwtIsOnBlacklist(jwt)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
