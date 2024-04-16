package com.example.Catalog.controller;

import com.example.Catalog.ProductsDto.*;
import com.example.Catalog.entities.ProductsEntity;
import com.example.Catalog.entities.UserDTO;
import com.example.Catalog.repositories.ProductsRepo;
import com.example.Catalog.services.ProductsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {


  @Autowired
  public ProductsService productsService;

  @Autowired
  public ProductsRepo productsRepo;


  @PostMapping(value = "/add")
  public ResponseEntity<String> addProducts(@RequestBody ProductsDto productsDto) {

    if (productsRepo.existsById(productsDto.getProductId()))
      return new ResponseEntity<String>("product id already exists add new product id", HttpStatus.OK);

    ProductsEntity currentProduct = new ProductsEntity();
    BeanUtils.copyProperties(productsDto, currentProduct);
    currentProduct.setProductName(productsDto.getProductName().toLowerCase());
    productsService.addProducts(currentProduct);

    return new ResponseEntity<String>("Added Product with id : " + currentProduct.getProductId(), HttpStatus.CREATED);
  }


  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
    if (productsService.deleteProduct(id)) {
      return new ResponseEntity<String>("Deleted product with id : " + id, HttpStatus.OK);
    }
    return new ResponseEntity<String>("product with id : " + id + " not Found!", HttpStatus.OK);
  }

  @PostMapping(value = "/update")
  public ResponseEntity<String> updateProduct(@RequestBody ProductsDto product) {
    ProductsEntity currentproduct = new ProductsEntity();
    BeanUtils.copyProperties(product, currentproduct);
    productsService.updateProduct(currentproduct);
    return new ResponseEntity<String>("product updated with id +:" + currentproduct.getProductId(), HttpStatus.OK);
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
      if (s.getProductId().equals(userDTO.getUserId()))
        l.add(s);
    }


    return new ResponseEntity<List<ProductsEntity>>(l, HttpStatus.OK);

  }


  @GetMapping(value = "/getByProductId/{id}")
  public ResponseEntity<List<ProductsEntity>> getByProductId(@PathVariable("id") String id) {
    List<ProductsEntity> l = new ArrayList<ProductsEntity>();
    Iterable<ProductsEntity> productsIterable = productsService.productsList();
    for (ProductsEntity s : productsIterable) {
      if (s.getProductId().equals(id))
        l.add(s);
    }


    return new ResponseEntity<List<ProductsEntity>>(l, HttpStatus.OK);

  }


  @GetMapping(value = "/getByCategory/{categoryId}")

  public ResponseEntity<ListOfProductItems> productsListByCategory(@PathVariable("categoryId") String categoryId) {

    List<ProductsDto> productByCategory = new ArrayList<>();
    for (ProductsEntity productsEntity : productsRepo.findAll()) {
      if (productsEntity.getCategoryId().equals(categoryId)) {
        ProductsDto productsDto = new ProductsDto();
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

  public ResponseEntity<List<ProductsDto>> productsListByMerchant(@PathVariable("merchantId") String merchantId) {

    List<ProductsDto> ProductByCategoryMerchant = new ArrayList<>();
    for (ProductsEntity productsEntity : productsRepo.findAll()) {
      if (productsEntity.getMerchantId().equals(merchantId)) {
        ProductsDto productsDto = new ProductsDto();
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


  @PostMapping("/increaseBuyersCount/{productId}")
  public void increaseBuyersCount(@PathVariable("productId") String productId) {
    productsService.countOfBuyers(productId);

  }


  @PostMapping("/reviewsForProduct")

  public String reviewsForProduct(@RequestBody ReviewDTO review) {
    return productsService.reviews(review.getReviewContent(), review.getUserID(), review.getProductId());
  }


}
