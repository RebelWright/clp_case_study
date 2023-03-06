package com.example.CLP_Case_Study.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder*/
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
    //@Builder.Default
    @Column(nullable=false)
    private double price = 0.00;
    @Column(nullable=false)
    private String imageUrl;
    @ManyToMany(mappedBy = "products")
    private List<Order> orders = new ArrayList<>();


    public Product() {
    }

    public Product(int productId, String productName, String productType, String productDescription, double price, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productDescription = productDescription;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String productName, String productType, String productDescription, double price, String imageUrl) {
        this.productName = productName;
        this.productType = productType;
        this.productDescription = productDescription;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Product))
            return false;
        if (obj == this)
            return true;
        return this.getProductId() == ((Product) obj).getProductId();
    }
}
