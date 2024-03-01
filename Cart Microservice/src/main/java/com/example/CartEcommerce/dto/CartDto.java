package com.example.CartEcommerce.dto;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class CartDto
{
    private String userId;

    private String productId;
}
