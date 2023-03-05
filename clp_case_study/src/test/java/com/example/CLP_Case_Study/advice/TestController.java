package com.example.CLP_Case_Study.advice;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String message) {
        return message;
    }

    @GetMapping("/sum")
    public int sum(@RequestParam int a, @RequestParam int b) {
        return a + b;
    }
}

