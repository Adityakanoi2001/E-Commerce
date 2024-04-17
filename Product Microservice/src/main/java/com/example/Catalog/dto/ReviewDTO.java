package com.example.Catalog.dto;

import lombok.Data;

@Data
public class ReviewDTO {

    private String userID;
    private String productId;
    private String reviewContent;
}
