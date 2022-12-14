package com.github.spencerk.ReimbursementAPI.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.BeanIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class UserAuth extends WebSecurityConfigurerAdapter {
    private final UserDetailsService  userDetailsService;
    private final JwtFilter           jwtRequestFilter;

    @Autowired
    public UserAuth(UserDetailsService userDetailsService, JwtFilter filter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .authorizeRequests().antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/employees/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/employees/").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.PUT, "/api/employees/").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.GET, "/api/employees/register").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.GET, "/api/employees/username/**").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.GET, "/api/employees/id/**").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.DELETE, "/api/employees/username/**").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.DELETE, "/api/employees/id/**").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.GET, "/api/tickets/**/**").hasAuthority("MANAGER")
                .antMatchers("tickets/status/**/**/**").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.PUT, "/api/tickets").hasAuthority("MANAGER")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
