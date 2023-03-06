package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private User mockedUserObject;
    @InjectMocks
    private UserService userService;

    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByCredentialsSuccess() {
        User mockUser = new User("test.com", "password", "John", "Doe", "JDoe");
        when(userRepository.findByEmailAndPassword("test.com", "password")).thenReturn(Optional.of(mockUser));
        Optional<User> resultUser = userService.findByCredentials("test.com", "password");
        assertEquals(resultUser.get().getEmail(), mockUser.getEmail());
        assertEquals(resultUser.get().getPassword(), mockUser.getPassword());
    }
    @Test
    void findByCredentialsSuccessFail() {
        User mockUser = new User("test.com", "password", "John", "Doe", "JDoe");
        when(userRepository.findByEmailAndPassword("test.com", "password")).thenReturn(Optional.of(mockUser));
        Optional<User> resultUser = userService.findByCredentials("test.com", "password");
        assertNotEquals("com.test", mockUser.getEmail());
        assertNotEquals("drowssap", mockUser.getPassword());
        assertNotEquals(resultUser.get().getEmail(), "com.test");
        assertNotEquals(resultUser.get().getPassword(), "drowssap");

    }

    @Test
    void saveUserTestSuccess() {
        when(userRepository.save(mockedUserObject)).thenReturn(mockedUserObject);
        User resultUser = userService.save(mockedUserObject);
        verify(userRepository, times(1)).save(mockedUserObject);
        assertEquals(mockedUserObject, resultUser);
    }
    @Test
    void saveUserTestFail() {
        //mockUser object intentionally has a null username field in order to fail test and trigger exception
        User mockUser = new User("test.com", "password", "John", "Doe", null);
        //using Exceptions for failing methods that don't return an object when succeeding
        when(userRepository.save(mockUser)).thenThrow(new IllegalArgumentException());
        /*assertThrows takes in 2 arguments(exceptions) and compares the first exception to the second
        using a lambda function that contains the code that should throw the exception*/
        assertThrows(IllegalArgumentException.class, () -> userService.save(mockUser));
        /*verify method is used to check if the method was executed. Verify testing the save method of userRepository
        one time and takes in mockUser as the argument of save
        */
        verify(userRepository, times(1)).save(mockUser);
    }


    @Test
    void findByIdTestSuccess() {
        User mockUser = new User("test.com", "password", "John", "Doe", "JDoe");
        mockUser.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        Optional<User> resultUser = userService.findById(1);
        assertEquals(resultUser.get().getUserId(), mockUser.getUserId());
    }
    @Test
    void findByIdTestFail() {
        User mockUser = new User("test.com", "password", "John", "Doe", "JDoe");
        mockUser.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        Optional<User> resultUser = userService.findById(1);
        assertNotEquals(resultUser.get().getUserId(), 100);
        assertNotEquals(100, mockUser.getUserId());
    }
}