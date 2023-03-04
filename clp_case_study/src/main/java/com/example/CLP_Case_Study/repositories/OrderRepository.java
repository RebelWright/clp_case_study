package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    //Optional<List<Product>> findAllByOrderId(int orderId);
    Optional<List<Order>> getAllOrdersByUser(User user);
    //Optional<List<Product>> findByAuthorInAndPostType(List<User> author, PostType postType);
    //Optional<List<Product>> findAllByUserAndPostType(User user, PostType postType);
    Optional<Order> findByOrderIdAndUser(int orderId, User user);
    /*@Modifying
    @Transactional
    @Query("UPDATE Order o SET o.products = :products, o.orderAmount = :orderAmount WHERE o.id = :id")
    void updateOrderProductsAndAmountById(@Param("id") int orderId, @Param("products") List<Product> products, @Param("orderAmount") double orderAmount);
*/

}
