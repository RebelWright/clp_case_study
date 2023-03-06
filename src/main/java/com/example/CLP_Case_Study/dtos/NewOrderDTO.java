package com.example.CLP_Case_Study.dtos;

import com.example.CLP_Case_Study.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder*/
public class NewOrderDTO {
    private int userId;
    //@Builder.Default
    private double orderAmount = 0;
}
