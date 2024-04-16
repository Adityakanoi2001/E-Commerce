package com.example.Catalog.ProductsDto;


import lombok.Data;

@Data
public class StockUpdateDto {
    private String productId;
    private String merchantId;
    private int stock;
}
