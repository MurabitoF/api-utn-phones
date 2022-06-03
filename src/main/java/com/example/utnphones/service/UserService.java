package com.example.utnphones.service;

import com.example.utnphones.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public Page<User> getAllUsers(Pageable pageable);
    public User saveNewUser(User newUser);
}
