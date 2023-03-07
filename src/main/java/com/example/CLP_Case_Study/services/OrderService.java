package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.interfaces.OrderServiceInterface;
import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.repositories.OrderRepository;
import com.example.CLP_Case_Study.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderService implements OrderServiceInterface {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, UserService userService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }
    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }
    public Order placeOrder(Order order) {return orderRepository.save(order);}

    public Order createOrder(int userId) {
        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        order.setOrderAmount(0);
        order.setUser(user);

        return orderRepository.save(order);
    }


    @Transactional
    public Order addProductToOrder(Order order, Product product) {
        List<Product> productList = new ArrayList<>(order.getProducts());
        productList.add(product);
        order.setProducts(productList);
        order.setOrderAmount(order.getOrderAmount() + product.getPrice()); // add product price to order amount
        return orderRepository.save(order);

    }


    /*@Transactional
    public Order deleteProductFromOrder(Order order, Product product) {
        List<Product> productList = new ArrayList<>(order.getProducts());
        if (productList.remove(product)) { // check if product is not null before removing
            order.setProducts(productList);
            order.setOrderAmount(order.getOrderAmount() - product.getPrice());
            Order updatedOrder = orderRepository.save(order);
            return updatedOrder;
        } else {
            throw new IllegalArgumentException("Product is null");
        }
    }*/
    @Transactional
    public Order deleteProductFromOrder(Order order, Product product) {
        List<Product> productList = new ArrayList<>(order.getProducts());
        productList.remove(product);  // check if product is not null before removing
        order.setProducts(productList);
        order.setOrderAmount(order.getOrderAmount() - product.getPrice());
        Order updatedOrder = orderRepository.save(order);
        return updatedOrder;
    }

}
