package com.example.Catalog.services;

import com.example.Catalog.dto.*;
import com.example.Catalog.entities.ProductsEntity;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

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

  /**
   * @param searchText
   * @return List of Products
   */
  public List<ProductResponseDto> getAllProductsBySearchTerm(String searchText);

  /**
   * @param page
   * @param size
   * @return
   */
  public Page<ProductResponseDto> getAllProducts(Integer page,Integer size);

  // --------- yet to be Modified -------------

    public void updateProduct(ProductsEntity currentproduct);

    public StockStatus updateStockValue(StockUpdateDto stockUpdateDto);
    public StockStatus increaseStock (StockUpdateDto stockUpdateDto);
    public StockStatus decreaseStock (StockDecreaseDto stockDecreaseDto);
    public ListOfProductEntities findByProductName(String merchantId,String productName);

    public Integer getStock(String productId);
    public int getRating(String productId,Integer currentRatingNew);

}
