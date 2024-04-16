package com.example.Catalog.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document // testing rating and noofbuyers on the new testcollection for bson error
public class ProductsEntity {

    @Id
    private String productId;
    private String productName;
    private String categoryId;
    private String productDescription;
    private String imageURL;
    private String merchantId;
    private String merchantName;
    private double price;
    private int stock;
    private String brand;

    private int rating=5;
    private int noOfBuyers=1;

    private List<Reviews> review;
}



