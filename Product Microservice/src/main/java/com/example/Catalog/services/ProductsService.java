package com.example.Catalog.services;

import com.example.Catalog.dto.*;
import com.example.Catalog.entities.ProductsEntity;

public interface ProductsService {
  /**
   * @param productInputDto
   */
  public void addNewProduct(ProductInputDto productInputDto);

  /**
   * @param productSkuId
   * @return
   */
  public boolean archiveOrDeleteProduct(String productSkuId);

  /**
   * @param productSkuId
   */
  public void incrementProductSaleCount(String productSkuId);

  /**
   * @param productReviewInputDto
   * @return boolean success/failure
   */
  public boolean addNewReviewForProduct(ProductReviewInputDto productReviewInputDto);

  /**
   * @param productSkuId
   * @return
   */
  public ProductResponseDto getProductByProductSkuId(String productSkuId);



  // --------- yet to be Modified -------------

    public void updateProduct(ProductsEntity currentproduct);
    public Iterable<ProductsEntity> productsList();
    public ListOfProductEntities getAllProductsBySearchTerm(String productName);

    public StockStatus updateStockValue(StockUpdateDto stockUpdateDto);
    public StockStatus increaseStock (StockUpdateDto stockUpdateDto);
    public StockStatus decreaseStock (StockDecreaseDto stockDecreaseDto);
    public ListOfProductEntities findByProductName(String merchantId,String productName);

    public Integer getStock(String productId);
    public int getRating(String productId,Integer currentRatingNew);

}
