package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //List<Product> findAllByProductId(int productId);
    Optional<List<Product>> findAllByProductType(String productType);
    //Optional<List<Product>> findByAuthorInAndPostType(List<User> author, PostType postType);
    //Optional<List<Product>> findAllByAuthorAndPostType(User user, PostType postType);

}
