package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByEmailAndPassword_thenReturnUser() {
        // Arrange
        User user = new User("john@example.com", "password", "John", "Doe", "https://example.com/flag.png");
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmailAndPassword("john@example.com", "password");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
    }

    @Test
    void whenFindByEmail_thenReturnUser() {
        // Arrange
        User user = new User("jane@example.com", "password", "Jane", "Doe", "https://example.com/flag.png");
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("jane@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void whenExistsByEmail_thenReturnTrue() {
        // Arrange
        User user = new User("joe@example.com", "password", "Joe", "Doe", "https://example.com/flag.png");
        userRepository.save(user);

        // Act
        boolean exists = userRepository.existsByEmail("joe@example.com");

        // Assert
        assertTrue(exists);
    }

    @Test
    void whenExistsByEmail_thenReturnFalse() {
        // Arrange
        User user = new User("mary@example.com", "password", "Mary", "Doe", "https://example.com/flag.png");
        userRepository.save(user);

        // Act
        boolean exists = userRepository.existsByEmail("john@example.com");

        // Assert
        assertFalse(exists);
    }
}