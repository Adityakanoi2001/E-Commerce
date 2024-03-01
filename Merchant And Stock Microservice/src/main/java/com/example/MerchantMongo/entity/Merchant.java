package com.example.MerchantMongo.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "merchant")

public class Merchant
{
    @Id
    private String merchantId; //Email
    private String merchantPassword;
    private String merchantName;
    private String merchantPhone;
    private String merchantCity;
    private String merchantState;
    private String company;
}
