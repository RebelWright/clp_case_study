package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetAll() {
        // given
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product(1, "product1", "type1", "description1", 10.00, "url1");
        Product product2 = new Product(2, "product2", "type2", "description2", 20.00, "url2");
        productList.add(product1);
        productList.add(product2);
        when(productRepository.findAll()).thenReturn(productList);

        // when
        List<Product> result = productService.getAll();

        // then
        assertThat(result).isEqualTo(productList);
    }

    @Test
    public void testGetAllByProductType() {
        // given
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product(1, "product1", "type1", "description1", 10.00, "url1");
        Product product2 = new Product(2, "product2", "type1", "description2", 20.00, "url2");
        productList.add(product1);
        productList.add(product2);
        when(productRepository.findAllByProductType("type1")).thenReturn(Optional.of(productList));

        // when
        Optional<List<Product>> result = productService.getAllByProductType("type1");

        // then
        assertThat(result).isNotEmpty();
        assertEquals(2, productList.size());
    }

    @Test
    public void testFindById() {
        // given
        int productId = 1;
        Product product = new Product(productId, "product1", "type1", "description1", 10.00, "url1");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        Optional<Product> result = productService.findById(productId);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(product);
    }
}
