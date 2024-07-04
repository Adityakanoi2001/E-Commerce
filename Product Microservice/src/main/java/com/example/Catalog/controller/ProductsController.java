package com.example.Catalog.controller;

import com.example.Catalog.dto.*;
import com.example.Catalog.entities.ProductsEntity;
import com.example.Catalog.helper.ProductCatalogApiPaths;
import com.example.Catalog.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

  @Operation(summary = "Increase Product Sale Count",
      description = "Increases the number of buyers for a specific product by its SKU ID")
  @PostMapping(ProductCatalogApiPaths.INCREASE_PRODUCT_SALE_COUNT)
  public ResponseEntity<Void> increaseBuyersCount(@Parameter(description = "SKU ID of the product to update",
      required = true) @PathVariable String productSkuId) {
    try {
      productsService.incrementProductSaleCount(productSkuId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
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
  public ResponseEntity<Page<ProductResponseDto>> getProductsWithSearchText(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size) {

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



  @PostMapping(value = "/update")
  public ResponseEntity<String> updateProduct(@RequestBody ProductInputDto product) {
    ProductsEntity currentproduct = new ProductsEntity();
    BeanUtils.copyProperties(product, currentproduct);
    productsService.updateProduct(currentproduct);
    return new ResponseEntity<String>("product updated with id +:" + currentproduct.getProductSkuId(), HttpStatus.OK);
  }


  @PostMapping(value = "/getProducts")
  public ResponseEntity<List<ProductsEntity>> getByProductId(@RequestBody UserDTO userDTO) {
    List<ProductsEntity> l = new ArrayList<ProductsEntity>();
    Iterable<ProductsEntity> productsIterable = productsService.productsList();
    for (ProductsEntity s : productsIterable) {
      if (s.getProductSkuId().equals(userDTO.getUserId()))
        l.add(s);
    }


    return new ResponseEntity<List<ProductsEntity>>(l, HttpStatus.OK);

  }

  @GetMapping(value = "/getByCategory/{categoryId}")

  public ResponseEntity<ListOfProductItems> productsListByCategory(@PathVariable("categoryId") String categoryId) {

    List<ProductInputDto> productByCategory = new ArrayList<>();
    for (ProductsEntity productsEntity : productsRepo.findAll()) {
      if (productsEntity.getCategoryId().equals(categoryId)) {
        ProductInputDto productsDto = new ProductInputDto();
        BeanUtils.copyProperties(productsEntity, productsDto);
        productByCategory.add(productsDto);
      }
    }
    if (!productByCategory.isEmpty()) {

      ListOfProductItems listOfProductItems = new ListOfProductItems();
      listOfProductItems.setProductsDtoList(productByCategory);

      return new ResponseEntity(listOfProductItems, HttpStatus.OK);
    } else {
      return new ResponseEntity("category id is null", HttpStatus.OK);
    }

  }

  @GetMapping(value = "/getByCategoryMerchant/{merchantId}")

  public ResponseEntity<List<ProductInputDto>> productsListByMerchant(@PathVariable("merchantId") String merchantId) {

    List<ProductInputDto> ProductByCategoryMerchant = new ArrayList<>();
    for (ProductsEntity productsEntity : productsRepo.findAll()) {
      if (productsEntity.getMerchantId().equals(merchantId)) {
        ProductInputDto productsDto = new ProductInputDto();
        BeanUtils.copyProperties(productsEntity, productsDto);
        ProductByCategoryMerchant.add(productsDto);
      }
    }
    if (!ProductByCategoryMerchant.isEmpty()) {

      ListOfProductItems listOfProductItems = new ListOfProductItems();
      listOfProductItems.setProductsDtoList(ProductByCategoryMerchant);

      return new ResponseEntity(listOfProductItems, HttpStatus.OK);
    } else {
      return new ResponseEntity("merchat id is not present", HttpStatus.OK);
    }


  }

  @GetMapping(value = "/getProductsWithName/{productName}")

  public ResponseEntity<ListOfProductItems> getProductsWithName(@PathVariable("productName") String productName) {

    return new ResponseEntity(productsService.getAllProductsBySearchTerm(productName), HttpStatus.OK);
  }


  @PostMapping(value = "/addStock")
  public ResponseEntity<StockStatus> callOtherServer(@RequestBody StockUpdateDto stockUpdateDto) {

    productsService.updateStockValue(stockUpdateDto);
    return new ResponseEntity("Stock Added ", HttpStatus.OK);
  }


  @PostMapping(value = "/updateStock")
  public ResponseEntity<StockStatus> updatestocknew(@RequestBody StockUpdateDto stockUpdateDto) {

    productsService.increaseStock(stockUpdateDto);
    return new ResponseEntity(" Stock Updated", HttpStatus.OK);
  }

  @PostMapping(value = "/decreaseStock")
  public ResponseEntity<StockStatus> decreasedStock(@RequestBody StockDecreaseDto stockDecreaseDto) {

    return new ResponseEntity(productsService.decreaseStock(stockDecreaseDto), HttpStatus.OK);
  }

  @GetMapping("/search/{searchTerm}")
  public ResponseEntity<ListOfProductEntities> getAllProductsBySearch(@PathVariable("searchTerm") String searchTerm) {

    return new ResponseEntity(productsService.getAllProductsBySearchTerm(searchTerm), HttpStatus.OK);
  }


  @GetMapping("/getStock/{productId}")
  public ResponseEntity<Stock> getStock(@PathVariable("productId") String productId) {
    Stock stock = new Stock();
    stock.setStock(productsService.getStock(productId));
    return new ResponseEntity(stock, HttpStatus.OK);
  }

  @PostMapping("/addRating/{productId}/{currentRatingNew}")
  public ResponseEntity<Integer> getRating(@PathVariable("productId") String productId,
      @PathVariable("currentRatingNew") Integer currentRatingNew) {
    return new ResponseEntity(productsService.getRating(productId, currentRatingNew), HttpStatus.OK);
  }

}
