package com.example.MerchantMongo.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Stock
{
    private String productId;
    private String merchantId;
    private int stock;

}
