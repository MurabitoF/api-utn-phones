package com.example.utnphones.service;

import com.example.utnphones.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public Page<User> getAllUsers(Pageable pageable);
    public User login(String username, String password);
    public User saveNewUser(User newUser);
}
