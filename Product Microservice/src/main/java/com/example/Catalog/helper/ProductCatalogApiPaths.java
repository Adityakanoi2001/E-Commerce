package com.example.Catalog.helper;

public interface ProductCatalogApiPaths {
  String BASE_PATH = Constants.CONTEXT_PATH + "ProductController";
  String ADD_NEW_REVIEW = "/addNewReviewForProduct";
  String INCREASE_PRODUCT_SALE_COUNT = "/increaseBuyersCount/{productId}";

}
