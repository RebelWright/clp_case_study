package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByProductId(int productId);
    //Optional<List<Product>> findByAuthorInAndPostType(List<User> author, PostType postType);
    //Optional<List<Product>> findAllByAuthorAndPostType(User user, PostType postType);

}
