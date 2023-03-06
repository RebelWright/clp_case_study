package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000","http://clp-case-study.cgzyfd7qztjb.us-east-2.rds.amazonaws.com"}, allowCredentials = "true")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(optionalProduct.get());
    }

    @GetMapping("/type/{productType}")
    public ResponseEntity<List<Product>> getAllProductsByType(@PathVariable String productType) {
        Optional<List<Product>> optionalProducts = productService.getAllByProductType(productType);
        if (!optionalProducts.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(optionalProducts.get());
    }

}
