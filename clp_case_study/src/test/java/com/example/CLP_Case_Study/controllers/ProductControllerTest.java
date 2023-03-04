package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProductsSuccess() throws Exception {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1, "product1", "type1", "description1", 10.00, "url1"));
        mockProducts.add(new Product(2, "product2", "type2", "description2", 20.00, "url2"));
        when(productService.getAll()).thenReturn(mockProducts);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is(1)))
                .andExpect(jsonPath("$[0].productName", is("product1")))
                .andExpect(jsonPath("$[1].productId", is(2)))
                .andExpect(jsonPath("$[1].productName", is("product2")));
    }

    @Test
    public void testGetProductByIdSuccess() throws Exception {
        Product mockProduct = new Product(1, "product1", "type1", "description1", 10.00, "url1");
        when(productService.findById(1)).thenReturn(Optional.of(mockProduct));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.productName", is("product1")));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        when(productService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void testGetAllProductsByTypeSuccess() throws Exception {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1, "product1", "type1", "description1", 10.00, "url1"));
        mockProducts.add(new Product(2, "product2", "type1", "description2", 20.00, "url2"));
        when(productService.getAllByProductType("type1")).thenReturn(Optional.of(mockProducts));

        mockMvc.perform(get("/products/type/type1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is(1)))
                .andExpect(jsonPath("$[0].productName", is("product1")))
                .andExpect(jsonPath("$[1].productId", is(2)))
                .andExpect(jsonPath("$[1].productName", is("product2")));
    }

    @Test
    public void testGetAllProductsByTypeNotFound() throws Exception {
        when(productService.getAllByProductType("type1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/type/type1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }
}
