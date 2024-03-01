package com.example.productsMongo.services;



import com.example.productsMongo.ProductsDto.*;
import com.example.productsMongo.entities.ProductsEntity;
import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public interface ProductsService {

    public void addProducts(ProductsEntity currentProduct);
    public boolean deleteProduct(String id);
    public void updateProduct(ProductsEntity currentproduct);
    public Iterable<ProductsEntity> productsList();
    public ListOfProductEntities getAllProductsBySearchTerm(String productName);

    public StockStatus updateStockValue(StockUpdateDto stockUpdateDto);
    public StockStatus increaseStock (StockUpdateDto stockUpdateDto);
    public StockStatus decreaseStock (StockDecreaseDto stockDecreaseDto);
    public ListOfProductEntities findByProductName(String merchantId,String productName);

    public Integer getStock(String productId);
    public int getRating(String productId,Integer currentRatingNew);

   public void countOfBuyers(String productId);
   public String reviews(String review,String userId,String productId);




}
