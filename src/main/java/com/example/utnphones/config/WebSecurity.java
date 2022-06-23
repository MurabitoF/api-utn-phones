package com.example.utnphones.config;

import com.example.utnphones.filter.JwtAuthorizationFilter;
import com.example.utnphones.model.Role;
import com.example.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurity(JwtAuthorizationFilter jwtAuthorizationFilter, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/swagger-ui", "/swagger-ui/index.html", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/calls/").hasAuthority(Role.ANTENNA.toString())
                .and()
                .authorizeRequests()
                .antMatchers("/api/clients/**").hasAnyAuthority(Role.EMPLOYEE.toString(), Role.CLIENT.toString())
                .and()
                .authorizeRequests()
                .antMatchers("/register").hasAuthority(Role.EMPLOYEE.toString())
                .antMatchers("/**").hasAuthority(Role.EMPLOYEE.toString())
                .anyRequest().authenticated();

        return http.build();
    }
}
