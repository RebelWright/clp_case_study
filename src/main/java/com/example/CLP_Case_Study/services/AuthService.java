package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.interfaces.AuthServiceInterface;
import com.example.CLP_Case_Study.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements AuthServiceInterface {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }

    public User register(User user) {
        return userService.save(user);
    }
}
