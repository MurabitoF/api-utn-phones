package com.example.utnphones.service.impl;

import com.example.utnphones.model.User;
import com.example.utnphones.repository.UserRepository;
import com.example.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);

    }

    @Override
    public User saveNewUser(User newUser) {
        return userRepository.save(newUser);
    }
}
