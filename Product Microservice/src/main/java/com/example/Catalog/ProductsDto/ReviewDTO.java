package com.example.Catalog.ProductsDto;

import lombok.Data;

@Data
public class ReviewDTO {

    private String userID;
    private String productId;
    private String reviewContent;
}
