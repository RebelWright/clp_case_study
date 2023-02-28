package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
