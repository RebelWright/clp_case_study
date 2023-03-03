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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Order mockOrder;

    @InjectMocks
    private OrderService orderService;
    @Mock
    private UserService userService;

    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
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
    void placeOrder() {
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
    void createOrderSuccess() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Order order = new Order();
        order.setUser(user);
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order result = orderService.createOrder(1);

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
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.createOrder(userId));
    }

    @Test
    void testAddProductToOrder() {
        // create user and order
        User user = new User(1,"test@test.com", "password", "John", "Doe", "url");
        Order testOrder = new Order();
        testOrder.setUser(user);
        testOrder = orderRepository.save(testOrder);

        //Order order = orderService.createOrder(1);
        when(orderService.createOrder(1)).thenReturn(testOrder);

        // create product
        Product testProduct = new Product(1,"test product", "type", "description", 10.00, "url");
        testProduct = productRepository.save(testProduct);

        // add product to order
        Order updatedOrder = orderService.addProductToOrder(mockOrder, testProduct);

        // assert that order was updated
        assertNotNull(updatedOrder);
        assertEquals(1, updatedOrder.getProducts().size());
        assertEquals(10.00, updatedOrder.getOrderAmount());
        assertEquals(testProduct, updatedOrder.getProducts().get(0));
    }

    @Test
    void testAddProductToOrder_withNullProduct() {
        // create user and order
        User user = new User(1,"test@test.com", "password", "John", "Doe", "url");
        Order order = new Order();
        order.setUser(user);
        order = orderRepository.save(order);


        // add null product to order
        Order updatedOrder = orderService.addProductToOrder(order, null);

        // assert that order was not updated
        assertNotNull(updatedOrder);
        assertEquals(0, updatedOrder.getProducts().size());
        assertEquals(0.00, updatedOrder.getOrderAmount());
    }

    @Test
    void testDeleteProductFromOrder() {
        User user = new User(1,"test@test.com", "password", "John", "Doe", "url");

        // Create a new order
        Order order = orderService.createOrder(1);

        // Add a product to the order
        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(9.99);
        product.setProductType("Test Type");
        product.setProductDescription("Test Description");
        product.setImageUrl("test-image-url");

        orderService.addProductToOrder(order, product);

        // Remove the product from the order
        Order updatedOrder = orderService.deleteProductFromOrder(order, product);

        // Assert that the product was removed from the order
        assertFalse(updatedOrder.getProducts().contains(product));

        // Assert that the order amount was updated correctly
        assertEquals(0, Double.compare(updatedOrder.getOrderAmount(), 0.0));
    }
}