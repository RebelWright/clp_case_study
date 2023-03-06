package com.example.CLP_Case_Study.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder*/
@Entity
@Table(name="orders")
@Component
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    // One order can contain many products
    //@Builder.Default
    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    private double orderAmount;

    // Many orders can belong to one user
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    public Order() {
    }

    public Order(int orderId, List<Product> products, double orderAmount, User user) {
        this.orderId = orderId;
        this.products = products;
        this.orderAmount = orderAmount;
        this.user = user;
    }

    public Order(List<Product> products, double orderAmount, User user) {
        this.products = new ArrayList<>();
        this.orderAmount = orderAmount;
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", products=" + products +
                ", orderAmount=" + orderAmount +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderId == order.orderId && Double.compare(order.orderAmount, orderAmount) == 0 && products.equals(order.products) && user.equals(order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, products, orderAmount, user);

    }
}