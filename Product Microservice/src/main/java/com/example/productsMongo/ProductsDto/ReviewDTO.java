package com.example.productsMongo.ProductsDto;

import lombok.Data;

@Data
public class ReviewDTO {

    private String userID;
    private String productId;
    private String reviewContent;
}
