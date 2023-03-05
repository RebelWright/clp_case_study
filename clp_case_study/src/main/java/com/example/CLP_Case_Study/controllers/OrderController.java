package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.dtos.NewOrderDTO;
import com.example.CLP_Case_Study.dtos.RegisterRequest;
import com.example.CLP_Case_Study.exceptions.ResourceNotFoundException;
import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.OrderService;
import com.example.CLP_Case_Study.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000","http:://clp-case-study.cgzyfd7qztjb.us-east-2.rds.amazonaws.com"}, allowCredentials = "true")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @PostMapping("/{userId}/createOrder")
    public ResponseEntity createOrder(@PathVariable("userId") int userId) {
        Optional<User> optionalUser = userService.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("No users were found with this id.");
        }

        Order newOrder = orderService.createOrder(userId);

        return ResponseEntity.ok(newOrder);
    }
    @PutMapping("/placeOrder/{orderId}")
    public ResponseEntity placeOrder(@PathVariable("orderId") int orderId) {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (!optionalOrder.isPresent()) {
            return ResponseEntity.badRequest().body("No order was found with this id.");
        }

        Order order = optionalOrder.get();
        Order updatedOrder = orderService.placeOrder(order);

        return ResponseEntity.ok(updatedOrder);
    }
    /*@PostMapping("/{orderId}/addProduct")
    public ResponseEntity<Order> addProductToOrder(@PathVariable("orderId") int orderId, @RequestBody Product product) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        orderService.addProductToOrder(order, product);
        return ResponseEntity.ok(order);
    }*/
    @PostMapping("/{orderId}/addProduct")
    public ResponseEntity<Order> addProductToOrder(@PathVariable("orderId") int orderId, @RequestBody Product product) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        Order updatedOrder = orderService.addProductToOrder(order, product);
        /*logger.info("Product {} added to order with ID {}", product.getProductName(), orderId);
        logger.debug("Updated order: {}", updatedOrder);*/
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/removeProduct")
    public ResponseEntity<Order> removeProductFromOrder(@PathVariable("orderId") int orderId, @RequestBody Product product) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        orderService.deleteProductFromOrder(order, product);
        return ResponseEntity.ok(order);
    }
}



