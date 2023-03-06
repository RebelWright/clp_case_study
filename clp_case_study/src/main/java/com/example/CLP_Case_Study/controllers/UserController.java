package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.OrderService;
import com.example.CLP_Case_Study.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000","http://clp-case-study.cgzyfd7qztjb.us-east-2.rds.amazonaws.com"}, allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;


    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable int id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("No users were found with this id.");
        }
        return ResponseEntity.ok(userOptional.get());
    }
   @GetMapping("/{id}/{orderId}")
    public ResponseEntity getOrderForUser(@PathVariable int id, @PathVariable int orderId) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("No users were found with this id.");
        }
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalOrder);
    }


}
