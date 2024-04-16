package com.example.Catalog.ProductsDto;


import lombok.Data;

@Data
public class StockDecreaseDto {

    private String productId;

    private Integer quantity;
}
