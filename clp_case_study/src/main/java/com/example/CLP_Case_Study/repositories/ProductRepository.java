package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    //Optional<List<Product>> findAllByProductType(String productType);
    @Query("SELECT p FROM Product p WHERE p.productType = :productType")
    Optional<List<Product>> findAllByProductType(@Param("productType") String productType);


}
