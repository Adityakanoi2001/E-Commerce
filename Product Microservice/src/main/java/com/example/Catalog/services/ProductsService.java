package com.example.Catalog.services;



import com.example.Catalog.dto.*;
import com.example.Catalog.entities.ProductsEntity;

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

   public void incrementProductSaleCount(String productSkuId);

    /**
     * @param productReviewInputDto
     * @return boolean success/failure
     */
   public boolean addNewReviewForProduct (ProductReviewInputDto productReviewInputDto);

}
