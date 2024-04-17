package com.example.Catalog.dto;


import lombok.Data;

@Data
public class StockDecreaseDto {

    private String productId;

    private Integer quantity;
}
