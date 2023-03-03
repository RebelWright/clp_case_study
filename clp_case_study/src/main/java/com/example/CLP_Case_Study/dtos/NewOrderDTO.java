package com.example.CLP_Case_Study.dtos;

import com.example.CLP_Case_Study.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDTO {
    private int userId;
    //private User user;
    private double orderAmount = 0;
}
