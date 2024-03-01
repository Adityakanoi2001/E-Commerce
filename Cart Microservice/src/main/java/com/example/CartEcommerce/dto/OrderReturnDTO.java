package com.example.CartEcommerce.dto;

import com.example.CartEcommerce.entites.ProductsEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OrderReturnDTO {

    private String userId;

    private List<ProductsEntity> productsEntities;

    private double totalCost;
}
