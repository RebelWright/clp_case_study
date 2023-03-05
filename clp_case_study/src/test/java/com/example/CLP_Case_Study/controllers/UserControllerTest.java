package com.example.CLP_Case_Study.controllers;

import com.example.CLP_Case_Study.models.Order;
import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.services.OrderService;
import com.example.CLP_Case_Study.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    //what we use in order to map our objects to JSon strings. For converting our objects to compare to expected
    //response from the servlet
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByIdTestSuccess() throws Exception {
        User testUser2 = new User(2, "test2.com", "password2", "Bob", "Smith", "image2.com");

        given(userService.findById(2)).willReturn(Optional.of(testUser2));
        this.mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(2)));
    }

    @Test
    void getUserByIdTestFail() throws Exception {
        User testUser2 = new User(2, "test2.com", "password2", "Bob", "Smith", "image2.com");

        given(userService.findById(3)).willReturn(Optional.empty());
        this.mockMvc.perform(get("/users/3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetOrderForUserSuccess() throws Exception {
        User mockUser = new User(1,"test.com", "password", "John", "Doe", "flag.url");
        Order mockOrder = new Order(1,new ArrayList<>(),0,mockUser);
        when(userService.findById(1)).thenReturn(Optional.of(mockUser));
        when(orderService.findById(1)).thenReturn(Optional.of(mockOrder));

        mockMvc.perform(get("/users/1/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.orderId", is(1)))
                .andExpect(jsonPath("$.user.userId", is(1)));
    }
    @Test
    public void testGetOrderForUserFail() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No users were found with this id."));
    }

   /* @Test
    public void testGetOrderForUserOrderNotFound() throws Exception {
        User mockUser = new User(1,"test.com", "password", "John", "Doe", "flag.url");
        when(userService.findById(1)).thenReturn(Optional.of(mockUser));
        when(orderService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1/1"))
                .andExpect(status().isBadRequest());
    }*/
}