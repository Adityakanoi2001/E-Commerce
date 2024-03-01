package com.example.productsMongo.ProductsDto;


import lombok.Data;

@Data
public class StockDecreaseDto {

    private String productId;

    private Integer quantity;
}
