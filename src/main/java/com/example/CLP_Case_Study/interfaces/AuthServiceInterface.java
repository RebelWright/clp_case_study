package com.example.CLP_Case_Study.interfaces;


import com.example.CLP_Case_Study.models.User;

import java.util.Optional;

public interface AuthServiceInterface {
    Optional<User> findByCredentials(String email, String password);
    User register(User user);
}
