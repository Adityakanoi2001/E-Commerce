package com.example.Catalog.services.serviceImpls;

import com.example.Catalog.dto.*;
import com.example.Catalog.entities.ProductsEntity;
import com.example.Catalog.entities.Reviews;
import com.example.Catalog.feign.FeignInterface;
import com.example.Catalog.helper.Constants;
import com.example.Catalog.repositories.ProductsRepository;
import com.example.Catalog.services.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductsServiceImpl implements ProductsService {

  @Autowired
  private ProductsRepository productsRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private FeignInterface feignInterface;

  // Add A New Product
  public void addNewProduct(ProductInputDto productInputDto) {
    log.info("Starting Function To Add New Product");
    ProductsEntity productsEntity = new ProductsEntity();
    BeanUtils.copyProperties(productInputDto, productsEntity);
    productsEntity.setProductSkuId(generateProductId());
    productsEntity.setActiveStatus(true);
    productsEntity.setDateAdded(new Date());
    productsEntity.setDateModified(new Date());
    HashMap<String, Double> priceList = new HashMap<>();
    priceList.put(productInputDto.getMerchantId(), productInputDto.getPrice());
    productsEntity.setPrice(priceList);
    productsRepository.save(productsEntity);
  }

  private String generateProductId() {
    AtomicLong productCounter = new AtomicLong(1);
    long currentCount = productCounter.getAndIncrement();
    return Constants.PRODUCT_ID_PREFIX + String.format(Constants.PRODUCT_ID_FORMAT,
        currentCount / 100000,
        currentCount % 100000);
  }

  // INCREASE NO OF BUYERS FOR A PRODUCT - INTERNAL CALLING WHEN ORDER IS DELIVERED
  public void incrementProductSaleCount(String productSkuId) {
    Query query = new Query(Criteria.where("productSkuId").is(productSkuId));
    Update update = new Update().inc("saleCount", 1);
    ProductsEntity updatedProduct = mongoTemplate.findAndModify(query,
        update,
        FindAndModifyOptions.options().returnNew(true).upsert(false),
        ProductsEntity.class);
    if (updatedProduct == null) {
      throw new IllegalArgumentException("Product with SKU ID " + productSkuId + " not found");
    }
  }

  //REVIEW API TO ADD REVIEW
  public boolean addNewReviewForProduct(ProductReviewInputDto productReviewInputDto) {
    try {
      Reviews reviews = new Reviews();
      BeanUtils.copyProperties(productReviewInputDto, reviews);
      reviews.setReviewId(UUID.randomUUID().toString());
      reviews.setDownVotes(0);
      reviews.setUpVotes(0);
      mongoTemplate.save(reviews);
      Query query = new Query();
      query.addCriteria(Criteria.where("productSkuId").is(productReviewInputDto.getProductSkuId()));
      Update update = new Update();
      update.push("reviewId", reviews.getReviewId());
      mongoTemplate.findAndModify(query, update, ProductsEntity.class);
      return true;
    } catch (IllegalArgumentException e) {
      log.error("Error: {}", e.getMessage());
      return false;
    } catch (Exception e) {
      log.error("An unexpected error occurred: {}", e.getMessage());
      return false;
    }
  }

  //Get Product Details With Product SKU ID
  @Override
  public ProductResponseDto getProductByProductSkuId(String productSkuId) {
    ProductResponseDto productResponseDto = new ProductResponseDto();
    ProductsEntity productsEntity = productsRepository.findByProductSkuId(productSkuId);
    BeanUtils.copyProperties(productsEntity, productResponseDto);
    // Fetch merchants asynchronously
    List<ExternalMerchantDto> merchants =
        CompletableFuture.supplyAsync(() -> feignInterface.getMerchantDetailList(productsEntity.getMerchantId()))
            .join();
    productResponseDto.setMerchants(merchants);
    // Create price map
    HashMap<ExternalMerchantDto, Double> priceMap = new HashMap<>();
    HashMap<String, Double> prices = productsEntity.getPrice();
    // Populate price map
    for (Map.Entry<String, Double> entry : prices.entrySet()) {
      String merchantId = entry.getKey();
      Double price = entry.getValue();
      for (ExternalMerchantDto merchant : merchants) {
        if (merchant.getMerchantId().equals(merchantId)) {
          priceMap.put(merchant, price);
          break;
        }
      }
    }
    productResponseDto.setPrice(priceMap);
    return productResponseDto;
  }

  //Archive a Product
  @Override
  public boolean archiveOrDeleteProduct(String productSkuId) {
    log.info("Function Execution for Deleting or Archive a Product From System : {}", productSkuId);
    ProductsEntity productsEntity = productsRepository.findByProductSkuId(productSkuId);
    if (Objects.nonNull(productsEntity)) {
      Query query = new Query();
      query.addCriteria(Criteria.where("productSkuId").is(productSkuId));
      Update update = new Update();
      update.push("activeStatus", false);
      mongoTemplate.findAndModify(query, update, ProductsEntity.class);
    }
    return false;
  }

  //Delete a Product with Certain Conditions
  @Scheduled(cron = "0 0 0 */7 * ?")
  public void deleteOldInactiveProducts() {
    List<ProductsEntity> productsEntityList = productsRepository.findAll();
    LocalDate currentDate = LocalDate.now();
    productsEntityList.stream()
        .filter(product -> product.isActiveStatus())
        .filter(product -> ChronoUnit.DAYS.between((Temporal) product.getDateModified(), currentDate) >= 21)
        .forEach(product -> productsRepository.delete(product));
  }

  // GET PRODUCTS DETAILS BY PRODUCT NAME - REGEX SEARCH
  @Override
  public List<ProductResponseDto> getAllProductsBySearchTerm(String searchText) {
    log.info("Search text: {}", searchText);
    List<ProductsEntity> productsEntities;
    if (searchText.length() < 3) {
      log.info("Search text is less than 3 characters. Returning all products.");
      productsEntities = mongoTemplate.findAll(ProductsEntity.class);
    } else {
      Pattern regs = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);
      Query query = new Query();
      query.addCriteria(Criteria.where("productName").regex(regs));
      log.info("Executing regex search for products with name matching: {}", searchText);
      productsEntities = mongoTemplate.find(query, ProductsEntity.class);
    }
    List<ProductResponseDto> productResponseDtoList = productsEntities.stream().map(entity -> {
      ProductResponseDto dto = new ProductResponseDto();
      BeanUtils.copyProperties(entity, dto);
      return dto;
    }).collect(Collectors.toList());
    log.info("Found {} products matching the search criteria.", productResponseDtoList.size());
    return productResponseDtoList;
  }

  //
  @Override
  public void updateProduct(ProductsEntity currentproduct) {
    productsRepo.save(currentproduct);
  }

  //GET ALL THE PRODUCTS
  @Override
  public Iterable<ProductsEntity> productsList() {
    return productsRepo.findAll();
  }


  //Update Stock - MERCHNAT USER
  @Override
  public StockStatus updateStockValue(StockUpdateDto stockUpdateDto) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id")
        .is(stockUpdateDto.getProductId())
        .and("merchantId")
        .is(stockUpdateDto.getMerchantId()));
    Update update = new Update();
    update.set("stock", stockUpdateDto.getStock());
    mongoTemplate.findAndModify(query, update, ProductsEntity.class);
    return null;
  }

  //INCREASE STOCK MERCHNAT USER
  @Override
  public StockStatus increaseStock(StockUpdateDto stockUpdateDto) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id")
        .is(stockUpdateDto.getProductId())
        .and("merchantId")
        .is(stockUpdateDto.getMerchantId()));

    List<ProductsEntity> productsEntityList = mongoTemplate.find(query, ProductsEntity.class);
    System.out.print(productsEntityList);

    ProductsEntity productsEntity = productsEntityList.get(0);
    int stock = stockUpdateDto.getStock() + productsEntity.getStock();
    Update update = new Update();
    update.set("productsEntities.stock", stock);
    mongoTemplate.findAndModify(query, update, ProductsEntity.class);

    return null;
  }

  // DECREASE STOCK - INTERNRANL CALLING WHEN ORDER IS PLACED FINGED IN ORDER CONTROLLLER
  @Override
  public StockStatus decreaseStock(StockDecreaseDto stockDecreaseDto) {
    Query query = new Query();

    query.addCriteria(Criteria.where("_id").is(stockDecreaseDto.getProductId()));

    List<ProductsEntity> productsEntityList = mongoTemplate.find(query, ProductsEntity.class);

    ProductsEntity productsEntity = productsEntityList.get(0);

    int stock = productsEntity.getStock() - stockDecreaseDto.getQuantity();

    Update update = new Update();

    update.set("stock", stock);

    mongoTemplate.findAndModify(query, update, ProductsEntity.class);

    StockStatus stockStatus = new StockStatus();
    stockStatus.setStatus("Updated");

    return stockStatus;
  }

  @Override
  public Integer getStock(String productId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(productId));
    List<ProductsEntity> productsEntitiesList = mongoTemplate.find(query, ProductsEntity.class);
    Integer currentStock = productsEntitiesList.get(0).getStock();
    return currentStock;
  }

  //RATING THE PRODUCT
  @Override
  public int getRating(String productId, Integer currentRatingNew) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(productId));
    List<ProductsEntity> productsEntityList = mongoTemplate.find(query, ProductsEntity.class);
    int currentRating = productsEntityList.get(0).getRating();
    Integer currentUsersCount = productsEntityList.get(0).getSaleCount();

    int newRating = (currentRating + currentRatingNew) / currentUsersCount;
    if (newRating < 1) {
      newRating = 1;
      Update update = new Update();
      update.set("rating", newRating);
      mongoTemplate.findAndModify(query, update, ProductsEntity.class);
    }
    Update update1 = new Update();
    update1.set("rating", newRating);
    mongoTemplate.findAndModify(query, update1, ProductsEntity.class);
    return newRating;
  }
}
