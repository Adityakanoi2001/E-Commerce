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
  public ResponseEntity<String> addNewProductToSystem (@RequestBody ProductInputDto productInputDto) {
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

  @Operation(summary = "Increase Product Sale Count", description = "Increases the number of buyers for a specific product by its SKU ID")
  @PostMapping(ProductCatalogApiPaths.INCREASE_PRODUCT_SALE_COUNT)
  public ResponseEntity<Void> increaseBuyersCount  (@Parameter(description = "SKU ID of the product to update", required = true) @PathVariable String productSkuId) {
    try {
      productsService.incrementProductSaleCount(productSkuId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(summary = "Add New Review for Product", description = "API to add a review for any product")
  @PostMapping(ProductCatalogApiPaths.ADD_NEW_REVIEW)
  public ResponseEntity<Boolean> addNewReviewForProduct (@RequestBody ProductReviewInputDto productReviewInputDto) {
    log.warn("Invoking API for Adding Product Review at Time: {}", new Date());
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

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
    if (productsService.deleteProduct(id)) {
      return new ResponseEntity<String>("Deleted product with id : " + id, HttpStatus.OK);
    }
    return new ResponseEntity<String>("product with id : " + id + " not Found!", HttpStatus.OK);
  }

  @PostMapping(value = "/update")
  public ResponseEntity<String> updateProduct(@RequestBody ProductInputDto product) {
    ProductsEntity currentproduct = new ProductsEntity();
    BeanUtils.copyProperties(product, currentproduct);
    productsService.updateProduct(currentproduct);
    return new ResponseEntity<String>("product updated with id +:" + currentproduct.getProductSkuId(), HttpStatus.OK);
  }


  @GetMapping(value = "/productList")

  public ResponseEntity<ListOfProductsItem> productsList() {
    List<ProductDtos> l = new ArrayList<ProductDtos>();
    Iterable<ProductsEntity> productsIterable = productsService.productsList();
    for (ProductsEntity s : productsIterable) {
      ProductDtos productsDto = new ProductDtos();
      BeanUtils.copyProperties(s, productsDto);
      l.add(productsDto);
    }
    ListOfProductsItem listOfProductItems = new ListOfProductsItem();
    listOfProductItems.setProductsDtoList(l);
    return new ResponseEntity(listOfProductItems, HttpStatus.OK);
  }

  //@PathVariable ("id") String id

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


  @GetMapping(value = "/getByProductId/{id}")
  public ResponseEntity<List<ProductsEntity>> getByProductId(@PathVariable("id") String id) {
    List<ProductsEntity> l = new ArrayList<ProductsEntity>();
    Iterable<ProductsEntity> productsIterable = productsService.productsList();
    for (ProductsEntity s : productsIterable) {
      if (s.getProductSkuId().equals(id))
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

  @GetMapping(value = "/findMerchnatByProductName/{merchantId}/{productName}")
  public ResponseEntity<ListOfProductEntities> MerchnatByProductName(@PathVariable("merchantId") String merchantId,
      @PathVariable("productName") String productName) {

    return new ResponseEntity(productsService.findByProductName(merchantId, productName), HttpStatus.OK);

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
