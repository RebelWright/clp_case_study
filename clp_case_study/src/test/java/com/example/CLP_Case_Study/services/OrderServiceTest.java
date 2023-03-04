package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.repositories.ProductRepository;
import com.example.CLP_Case_Study.repositories.UserRepository;
import com.example.CLP_Case_Study.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;
    @Mock
    private UserService userService;

    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdSuccess() {
        int orderId = 1;
        List<Product> products = new ArrayList<>();
        double orderAmount = 10.0;
        User user = new User();
        Order expectedOrder = new Order(orderId, products, orderAmount, user);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Optional<Order> actualOrder = orderService.findById(orderId);

        // Assert
        assertTrue(actualOrder.isPresent());
        assertEquals(expectedOrder, actualOrder.get());
    }

    @Test
    void testPlaceOrderSuccess() {
        // Arrange
        int orderId = 1;
        List<Product> products = new ArrayList<>();
        double orderAmount = 10.0;
        User user = new User();
        Order order = new Order(orderId, products, orderAmount, user);
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order actualOrder = orderService.placeOrder(order);

        // Assert
        assertNotNull(actualOrder);
        assertEquals(order, actualOrder);
    }

    @Test
    void testCreateOrderSuccess() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setUserId(userId);
        when(userService.findById(userId)).thenReturn(Optional.of(user));

        Order order = new Order();
        order.setUser(user);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order result = orderService.createOrder(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUser().getUserId());
        assertEquals(0, result.getOrderAmount());
        assertTrue(result.getProducts().isEmpty());
    }

    @Test
    void createOrder_userNotFound_throwException() {
        // Arrange
        int userId = 1;
        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.createOrder(2));
    }

    @Test
    void testAddProductToOrder() {
        // create user and order
        User user = new User(1,"test@test.com", "password", "John", "Doe", "url");
        when(userService.findById(1)).thenReturn(Optional.of(user));
        Order order = new Order(1,new ArrayList<>(),0,user);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        order = orderService.createOrder(1);

        // create product
        Product product = new Product(1,"Test Product","Test Type","Test Description",10,"test.url");

        // add product to order
        Order updatedOrder = orderService.addProductToOrder(order, product);

        // assert that order was updated
        assertNotNull(updatedOrder);
        assertEquals(1, updatedOrder.getProducts().size());
        assertEquals(10.00, updatedOrder.getOrderAmount());
        assertEquals(product, updatedOrder.getProducts().get(0));
    }

    @Test
    void testDeleteProductFromOrder() {
        User user = new User(1,"test@test.com", "password", "John", "Doe", "url");

        // Create a new order
        Order order = new Order(1,new ArrayList<>(),0,user);
        // Add a product to the order
        Product product = new Product(1,"Test Product","Test Type","Test Description",9.99,"test.url");

        orderService.addProductToOrder(order, product);

        // Remove the product from the order
        Order updatedOrder = orderService.deleteProductFromOrder(order, product);

        // Assert that the product was removed from the order
        assertFalse(updatedOrder.getProducts().contains(product));

        // Assert that the order amount was updated correctly
        assertEquals(0, Double.compare(updatedOrder.getOrderAmount(), 0.0));
    }
}