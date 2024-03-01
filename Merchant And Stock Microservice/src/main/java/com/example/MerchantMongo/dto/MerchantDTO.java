package com.example.MerchantMongo.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTO
{

    private String merchantId; //email
    private String merchantPassword;
    private String merchantName;
    private String merchantPhone;
    private String merchantCity;
    private String merchantState;
    private String company;

}
