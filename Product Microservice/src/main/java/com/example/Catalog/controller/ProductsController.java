package com.example.Catalog.controller;

import com.example.Catalog.dto.*;
import com.example.Catalog.entities.Category;
import com.example.Catalog.entities.ProductsEntity;
import com.example.Catalog.helper.GraphQLResolver;
import com.example.Catalog.helper.ProductCatalogApiPaths;
import com.example.Catalog.services.ProductsService;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = ProductCatalogApiPaths.BASE_PATH)
public class ProductsController {

  @Autowired
  private ProductsService productsService;

  @Autowired
  private GraphQLResolver graphQLResolver;

  @Operation(summary = "Add New Product", description = "API to add a New Product to the System")
  @PostMapping(ProductCatalogApiPaths.ADD_NEW_PRODUCT)
  public ResponseEntity<String> addNewProductToSystem(@RequestBody ProductInputDto productInputDto) {
    log.info("Invoking API for Adding New Product at Time: {}", new Date());
    try {
      productsService.addNewProduct(productInputDto);
      log.info("Added Product with Name: {}", productInputDto.getProductName());
      return new ResponseEntity<>("Added Product with ID: " + productInputDto.getProductName(), HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("An error occurred while adding the product: {}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Add New Review for Product", description = "API to add a review for any product")
  @PostMapping(ProductCatalogApiPaths.ADD_NEW_REVIEW)
  public ResponseEntity<Boolean> addNewReviewForProduct(@RequestBody ProductReviewInputDto productReviewInputDto) {
    log.info("Invoking API for Adding Product Review at Time: {}", new Date());
    try {
      boolean result = productsService.addNewReviewForProduct(productReviewInputDto);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      log.error("Error adding review: {}", e.getMessage());
      return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      log.error("An unexpected error occurred: {}", e.getMessage());
      return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Delete or Archive a Product", description = "API to Delete or Archive a Product")
  @DeleteMapping(ProductCatalogApiPaths.ARCHIVE_DELETE_PRODUCT)
  public ResponseEntity<String> archiveOrDeleteProduct(@PathVariable String productSkuId) {
    log.info("Invoking API to Archive Or Delete a Product");
    if (productsService.archiveOrDeleteProduct(productSkuId)) {
      return new ResponseEntity<String>("Deleted product with id : " + productSkuId, HttpStatus.OK);
    }
    return new ResponseEntity<String>("product with id : " + productSkuId + " not Found!",
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Operation(summary = "Get Product Details", description = "API to Get Product Details")
  @GetMapping(ProductCatalogApiPaths.GET_PRODUCT_BY_PRODUCT_SKU_CODE)
  public ResponseEntity<ProductResponseDto> getProductByProductSkuId(@PathVariable String productSkuId) {
    log.info("Invoking API to Get a Product Details with ProductSkuId {} at Time : {}", productSkuId, new Date());
    try {
      ProductResponseDto productResponseDto = productsService.getProductByProductSkuId(productSkuId);
      log.info("Details of Product Retrieved Successfully: {}", productResponseDto.getProductName());
      return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("An error occurred while getting the Product Details: {}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get Product Details By Search Terms", description = "API to Get Product Details by Search Term")
  @GetMapping(ProductCatalogApiPaths.GET_LIST_OF_PRODUCT_BY_SEARCH_TERM)
  public ResponseEntity<List<ProductResponseDto>> getProductsWithSearchText(@PathVariable(
      "searchText") String searchText) {
    log.info("Invoking API to Get Products with Name {} at Time : {}", searchText, new Date());
    try {
      List<ProductResponseDto> products = productsService.getAllProductsBySearchTerm(searchText);
      if (products.isEmpty()) {
        log.info("No Products Found with Name: {}", searchText);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      log.info("Products Retrieved Successfully with Name: {}", searchText);
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (Exception e) {
      log.error("An error occurred while getting the Products with Name {}: {}", searchText, e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get All Products", description = "API to Get All Products")
  @GetMapping(ProductCatalogApiPaths.GET_ALL_PRODUCTS_LIST)
  public ResponseEntity<Page<ProductResponseDto>> getProductsWithSearchText(@RequestParam(value = "page",
      required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {

    log.info("Invoking API to Get All Products at Time : {}", new Date());
    try {
      Page<ProductResponseDto> products = productsService.getAllProducts(page, size);
      if (products.isEmpty()) {
        log.info("No Products Found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      log.info("Products Retrieved Successfully");
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (Exception e) {
      log.error("An error occurred while getting the Products {}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Add Rating to Product", description = "API to Add Rating to a Product")
  @PostMapping(ProductCatalogApiPaths.PRODUCT_RATING)
  public ResponseEntity<Void> productRating(@RequestBody RatingInputDto ratingInputDto) {
    log.info("Invoking API to Add Rating for Product ID {} with New Rating {} at Time: {}",
        ratingInputDto.getProductSkuId(),
        ratingInputDto.getRatingValue(),
        new Date());
    try {
      productsService.productRating(ratingInputDto);
      log.info("Rating Added Successfully for Product ID: {}", ratingInputDto.getProductSkuId());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      log.error("An error occurred while adding the Rating for Product ID {}: {}",
          ratingInputDto.getProductSkuId(),
          e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Add Category", description = "API to add a new category")
  @PostMapping(ProductCatalogApiPaths.ADD_NEW_CATEGORY)
  public ResponseEntity<Void> addCategory(@RequestBody CategoryInputDto categoryInputDto) {
    try {
      Boolean response =
          graphQLResolver.createCategory(categoryInputDto.getCategoryId(), categoryInputDto.getCategoryName());
      if (response) {
        log.info("Category Added Successfully with ID: {}", categoryInputDto.getCategoryId());
        return new ResponseEntity<>(HttpStatus.CREATED);
      } else {
        log.error("Failed to add Category with ID: {}", categoryInputDto.getCategoryId());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("An error occurred while adding the Category with ID {}: {}",
          categoryInputDto.getCategoryId(),
          e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Add Category", description = "API to add a new category")
  @PostMapping(ProductCatalogApiPaths.GET_ALL_AVAILABLE_CATEGORY)
  public ResponseEntity<List<Category>> getAllCategoryAvailable() {
    try {
      List<Category> categoryList = graphQLResolver.getAllCategory();
      if (categoryList.isEmpty()) {
        log.info("No Category Available");
        return new ResponseEntity<>(categoryList, HttpStatus.NO_CONTENT);
      } else {
        log.error("Failed to Fetch Categories");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("An error occurred while adding the Category {}", e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = ProductCatalogApiPaths.UPDATE_PRODUCT)
  public ResponseEntity<String> updateProductInformation(@RequestBody ProductInputDto productInputDto) {
    try {
      ProductsEntity productsEntity = new ProductsEntity();
      BeanUtils.copyProperties(productInputDto, productsEntity);
      boolean isUpdated = productsService.updateProductInformation(productsEntity);

      if (isUpdated) {
        log.info("Product updated successfully with SKU ID: {}", productsEntity.getProductSkuId());
        return new ResponseEntity<>("Product updated with SKU ID: " + productsEntity.getProductSkuId(), HttpStatus.OK);
      } else {
        log.error("Failed to update product with SKU ID: {}", productsEntity.getProductSkuId());
        return new ResponseEntity<>("Failed to update product", HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
      log.error("An error occurred while updating the product: {}", e.getMessage());
      return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Get Products by Category", description = "Returns a list of products by category ID")
  @GetMapping(value = ProductCatalogApiPaths.GET_ALL_PRODUCTS_BY_CATEGORY_MAPPING)
  public ResponseEntity<List<ProductResponseDto>> productsListByCategory(@PathVariable(
      "categoryId") String categoryId) {
    try {
      List<ProductResponseDto> products = productsService.getProductsByCategory(categoryId);
      if (!products.isEmpty()) {
        return ResponseEntity.ok(products);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (IllegalArgumentException e) {
      log.error("Invalid categoryId provided: {}", categoryId, e);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      log.error("Error fetching products for categoryId: {}", categoryId, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
