package com.example.CLP_Case_Study.controllers;

import static org.junit.jupiter.api.Assertions.*;
import com.example.CLP_Case_Study.models.Product;
import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.OrderService;
import com.example.CLP_Case_Study.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createOrderTestFail() throws Exception {
        int userId = 1;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/orders/{userId}/createOrder", userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No users were found with this id."));
    }

    @Test
    public void createOrderTestSuccess() throws Exception {
        int userId = 1;
        User user = new User(1,"test.com","password", "John", "Doe", "flagURL");
        when(userService.findById(userId)).thenReturn(Optional.of(user));

        Order order = new Order(1, new ArrayList<>(),0,user);
        order.setUser(user);
        when(orderService.createOrder(userId)).thenReturn(order);

        mockMvc.perform(post("/orders/{userId}/createOrder", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.userId", is(userId)));
    }

    @Test
    public void placeOrderTestFail() throws Exception {
        int orderId = 1;
        when(orderService.findById(orderId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/orders/placeOrder/{orderId}", orderId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No order was found with this id."));
    }

    @Test
    public void placeOrderTestSuccess() throws Exception {
        User user = new User(1,"test.com","password", "John", "Doe", "flagURL");
        int orderId = 1;
        Order order = new Order(1,new ArrayList<>(),10.0,user);
        when(orderService.findById(orderId)).thenReturn(Optional.of(order));
        when(orderService.placeOrder(order)).thenReturn(order);

        mockMvc.perform(put("/orders/placeOrder/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(orderId)))
                .andExpect(jsonPath("$.orderAmount", is(10.0)));
    }

    @Test
    public void addProductToOrderTestSuccess() throws Exception {
        User user = new User(1,"test.com","password", "John", "Doe", "flagURL");
        int orderId = 1;
        List<Product> products = new ArrayList<>();
        Order order = new Order(orderId, products, 0, user);
        when(orderService.findById(orderId)).thenReturn(Optional.of(order));

        Product product = new Product(1,"testName","testType","description", 10,"image.url");
        List<Product> updatedProducts = new ArrayList<>(products);
        updatedProducts.add(product);
        Order expectedOrder = new Order(orderId, updatedProducts, 10, user);
        given(orderService.addProductToOrder(order, product)).willReturn(expectedOrder);

        mockMvc.perform(post("/orders/{orderId}/addProduct", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId", is(orderId)))
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.orderAmount", is(10.0)));

    }

    @Test
    public void removeProductFromOrderTestSuccess() throws Exception {
        // Setup
        User user = new User(1,"test.com","password", "John", "Doe", "flagURL");
        int orderId = 1;
        Product product = new Product(1, "Product1", "Type1", "Description1", 10.0, "http://imageUrl1");
        Order order = new Order(orderId, Collections.singletonList(product), 10.0, user);
        given(orderService.findById(orderId)).willReturn(Optional.of(order));
        given(orderService.deleteProductFromOrder(order, product)).willReturn(order);

        // Execution and verification
        mockMvc.perform(post("/orders/{orderId}/removeProduct", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(orderId)));
        verify(orderService, times(1)).findById(orderId);
        verify(orderService, times(1)).deleteProductFromOrder(order, product);
    }
}