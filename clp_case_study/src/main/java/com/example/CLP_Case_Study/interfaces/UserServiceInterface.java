package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    Optional<User> findByCredentials(String email, String password);

    User save(User user);

    Optional<User> findById(int userId);

    Optional<User> findByEmail(String email);

    Optional<List<Order>> getAllOrdersByUser(User user);

    Optional<Order> findByOrderId(int orderId);
}
