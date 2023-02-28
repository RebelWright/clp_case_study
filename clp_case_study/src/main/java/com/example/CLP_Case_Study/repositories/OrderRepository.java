package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Product> findAllByOrderId(int orderId);
    //Optional<List<Product>> findByAuthorInAndPostType(List<User> author, PostType postType);
    //Optional<List<Product>> findAllByUserAndPostType(User user, PostType postType);

}
