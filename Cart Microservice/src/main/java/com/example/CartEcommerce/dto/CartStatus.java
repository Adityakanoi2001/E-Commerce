package com.example.CartEcommerce.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartStatus {

    private String status;

    private double totalCost;
}
