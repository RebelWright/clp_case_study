package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.repositories.OrderRepository;
import com.example.CLP_Case_Study.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.CLP_Case_Study.services.UserServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    public UserService(UserRepository userRepository, OrderRepository orderRepository) {

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user)
    {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }


    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    public Optional<List<Order>> getAllOrdersByUser(User user) {return orderRepository.getAllOrdersByUser(user);}
    public Optional<Order> findByOrderId(int orderId) {return orderRepository.findById(orderId);}
}

