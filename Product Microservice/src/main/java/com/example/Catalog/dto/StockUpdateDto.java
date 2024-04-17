package com.example.Catalog.dto;


import lombok.Data;

@Data
public class StockUpdateDto {
    private String productId;
    private String merchantId;
    private int stock;
}
