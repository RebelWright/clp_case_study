package com.example.CLP_Case_Study.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
@Component
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;


    @Column(nullable=false)
    private String productName;
    @Column(nullable = false)
    private String productType;
    @Column(nullable=false)
    private String productDescription;

    @Column(nullable=false)
    private double price = 0.00;
    @Column(nullable=false)
    private String imageUrl;



}
