package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByOrderIdAndUser() {
        // Create a test user
        User user = new User(1,"test@gmail.com", "password", "John", "Doe", "https://flag.com");
        userRepository.save(user);
        // Create a test product
        Product product = new Product(1,"Test Product", "Test description", "Test category", 10.0, "https://test.com");
        productRepository.save(product);
        List<Product> products = new ArrayList<>();
        products.add(product);

        // Create a test order
        Order order = new Order(1,products,10.0,user);
        orderRepository.save(order);

        // Call the method being tested
        Optional<Order> foundOrder = orderRepository.findByOrderIdAndUser(order.getOrderId(), user);

        // Check that the order was found
        assertTrue(foundOrder.isPresent());

        // Check that the properties of the order are as expected
        Order expectedOrder = new Order(order.getOrderId(), products, 10.0, user);
        assertEquals(expectedOrder.toString(), foundOrder.get().toString());
    }
    @Test
    public void saveOrderTestSuccess() {
        User user = new User(1,"test@gmail.com", "password", "John", "Doe", "https://flag.com");
        Product product1 = new Product("Product 1","Type A","Description A", 10.99, "image.com");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        Order order = new Order(1,products,10.00, user);
        Order savedOrder = new Order(1, products, 10.00, user);
        orderRepository.save(order);

        // Then
        assertNotNull(savedOrder.getOrderId());
        assertEquals(order.getOrderAmount(), savedOrder.getOrderAmount());
        assertEquals(order.getUser(), savedOrder.getUser());
    }
}