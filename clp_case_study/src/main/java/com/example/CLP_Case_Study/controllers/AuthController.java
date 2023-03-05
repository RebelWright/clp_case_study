package com.example.CLP_Case_Study.controllers;


import com.example.CLP_Case_Study.dtos.LoginRequest;
import com.example.CLP_Case_Study.dtos.RegisterRequest;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000","http:://clp-case-study.cgzyfd7qztjb.us-east-2.rds.amazonaws.com"}, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> userOptional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if(!userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("user", userOptional.get());

        return ResponseEntity.ok(userOptional.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User created = new User(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getFlagURL());

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
    }
}

