package com.example.CLP_Case_Study.interfaces;


import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {

    Optional<Order> findById(int id);

    Order placeOrder(Order order);

    Order addProductToOrder(Order order, Product product);

    Order deleteProductFromOrder(Order order, Product product);

    List<Order> getAllOrdersByUser(User user);
}