package com.example.CartEcommerce.dto;

import com.example.CartEcommerce.entites.ProductsEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CartReturnDto {


    private String userId;

    private List<ProductsEntity> productsEntities;

    private double totalCost;
}
