package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmailAndPassword() {
        // Given
        User user = new User("test@gmail.com", "password", "John", "Doe", "https://flag.com");
        userRepository.save(user);

        // When
        Optional<User> optionalUser = userRepository.findByEmailAndPassword("test@gmail.com", "password");

        // Then
        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals("John", optionalUser.get().getFirstName());
        Assertions.assertEquals("Doe", optionalUser.get().getLastName());
    }

    @Test
    public void testExistsByEmail() {
        // Given
        User user = new User("test@gmail.com", "password", "John", "Doe", "https://flag.com");
        userRepository.save(user);

        // When
        boolean existsByEmail = userRepository.existsByEmail("test@gmail.com");
        boolean notExistsByEmail = userRepository.existsByEmail("notexists@gmail.com");

        // Then
        Assertions.assertTrue(existsByEmail);
        Assertions.assertFalse(notExistsByEmail);
    }
}