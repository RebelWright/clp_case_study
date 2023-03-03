package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.interfaces.ProductServiceInterface;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }
    public Optional<List<Product>> getAllByProductType(String productType) {return this.productRepository.findAllByProductType(productType);}
    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

}
