package com.example.CartEcommerce.dto;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockCheckDto
{
    String status;
    int currentStock;

}
