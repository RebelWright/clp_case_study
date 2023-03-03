package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.dtos.NewOrderDTO;
import com.example.CLP_Case_Study.dtos.RegisterRequest;
import com.example.CLP_Case_Study.exceptions.ResourceNotFoundException;
import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.OrderService;
import com.example.CLP_Case_Study.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"}, allowCredentials = "true")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /*@PostMapping("/{userId}/createOrder")
    public ResponseEntity createOrder(@PathVariable("userId") int userId, @RequestBody NewOrderDTO orderDTO) {
        orderDTO.setUserId(userId);
        Optional<User> optionalUser = userService.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("No users were found with this id.");
        }

        Order newOrder = orderService.createOrder(orderDTO, userId);

        return ResponseEntity.ok(newOrder);
    }*/
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
    @PostMapping("/{orderId}/addProduct")
    public ResponseEntity<Order> addProductToOrder(@PathVariable("orderId") int orderId, @RequestBody Product product) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        orderService.addProductToOrder(order, product);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/removeProduct")
    public ResponseEntity<Order> removeProductFromOrder(@PathVariable("orderId") int orderId, @RequestBody Product product) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        orderService.deleteProductFromOrder(order, product);
        return ResponseEntity.ok(order);
    }
}



