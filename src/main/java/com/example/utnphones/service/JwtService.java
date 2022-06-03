package com.example.utnphones.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface JwtService {
    public String createToken(UserDetails userDetails);
    public Boolean hasTokenExpired(String token);
    public Boolean validateToken(String token, UserDetails userDetails);
    public String extractUsername(String token);
    public Collection<? extends GrantedAuthority> getAuthorities(String token);
}
