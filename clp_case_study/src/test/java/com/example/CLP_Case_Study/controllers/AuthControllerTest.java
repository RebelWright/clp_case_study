package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.dtos.LoginRequest;
import com.example.CLP_Case_Study.dtos.RegisterRequest;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService);
    }

    @Test
    void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest("email@example.com", "password");
        User mockUser = new User(loginRequest.getEmail(), loginRequest.getPassword(), "first name", "last name", "flag url");

        when(authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword())).thenReturn(Optional.of(mockUser));

        ResponseEntity<User> response = authController.login(loginRequest, httpSession);

        verify(httpSession).setAttribute("user", mockUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void loginFail() {
        LoginRequest loginRequest = new LoginRequest("email@example.com", "password");

        when(authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword())).thenReturn(Optional.empty());

        ResponseEntity<User> response = authController.login(loginRequest, httpSession);

        verify(httpSession, never()).setAttribute(anyString(), any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void logout() {
        ResponseEntity<Void> response = authController.logout(httpSession);

        verify(httpSession).removeAttribute("user");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void register() {
        RegisterRequest registerRequest = new RegisterRequest("email@example.com", "password", "first name", "last name", "flag url");
        User mockUser = new User(registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getFlagURL());

        when(authService.register(any(User.class))).thenReturn(mockUser);

        ResponseEntity<User> response = authController.register(registerRequest);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }
}