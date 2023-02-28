package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://flutterdeployedbucket.s3-website-us-east-1.amazonaws.com"}, allowCredentials = "true")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Testing Method: Adds a User
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable int id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("No users were found with this id.");
        }
        return ResponseEntity.ok(userOptional.get());
    }
    @GetMapping("/{id}/order")
    public ResponseEntity getFeedForUser(@PathVariable int id) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("No users were found with this id.");
        }
        List<Product> order = userService.getFeedForUser(optionalUser.get());
        if (order == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity getAllPostsByAUser(@PathVariable int id) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("No users were found with this id.");
        }
        Optional<List<Post>> postList = userService.getAllPostsByAUser(optionalUser.get());
        if (!postList.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(postList.get());
    }
}
