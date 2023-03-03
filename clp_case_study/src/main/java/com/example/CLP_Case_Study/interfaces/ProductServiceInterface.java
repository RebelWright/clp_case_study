package com.example.CLP_Case_Study.interfaces;

import com.example.CLP_Case_Study.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {
    List<Product> getAll();
    Optional<List<Product>> getAllByProductType(String productType);
    Optional<Product> findById(int productId);
}
