package com.example.Catalog.services.serviceImpls;

import com.example.Catalog.dto.*;
import com.example.Catalog.entities.Category;
import com.example.Catalog.entities.ProductsEntity;
import com.example.Catalog.entities.Reviews;
import com.example.Catalog.feign.FeignInterface;
import com.example.Catalog.helper.Constants;
import com.example.Catalog.helper.GraphQLResolver;
import com.example.Catalog.repositories.ProductsRepository;
import com.example.Catalog.services.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import static com.example.Catalog.helper.Constants.DEFAULT_PAGE;
import static com.example.Catalog.helper.Constants.DEFAULT_SIZE;
import static com.example.Catalog.helper.Constants.MAX_SIZE;

@Service
@Slf4j
public class ProductsServiceImpl implements ProductsService {

  @Autowired
  private ProductsRepository productsRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private FeignInterface feignInterface;

  @Autowired
  private GraphQLResolver graphQLResolver;

  // Add A New Product To The System
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
    bifurcateStock(productsEntity, productInputDto.getStock());
    productsRepository.save(productsEntity);
    graphQLResolver.addProductToCategory(productInputDto.getCategoryId(), productsEntity.getProductSkuId());
  }

  private void bifurcateStock(ProductsEntity productEntity, int totalStock) {
    double reservedStockRatio = 0.20;
    double saleStockRatio = 0.30;
    int reservedStock = (int) (totalStock * reservedStockRatio);
    int saleStock = (int) (totalStock * saleStockRatio);
    int stock = totalStock - reservedStock - saleStock;
    productEntity.setReservedStock(reservedStock);
    productEntity.setSaleStock(saleStock);
    productEntity.setStock(stock);
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

  //RATING THE PRODUCT
  @Override
  public void productRating(RatingInputDto ratingInputDto) {
    log.info("Starting productRating method for Product SKU ID: {}", ratingInputDto.getProductSkuId());
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("productSkuId").is(ratingInputDto.getProductSkuId()));
      ProductsEntity productEntity = mongoTemplate.findOne(query, ProductsEntity.class);
      if (productEntity == null) {
        log.warn("No product found with SKU ID: {}", ratingInputDto.getProductSkuId());
        return;
      }
      int currentRating = productEntity.getRating();
      int currentUsersCount = productEntity.getSaleCount(); // Assuming saleCount is used for number of ratings
      if (currentUsersCount == 0) {
        log.warn("Current users count is zero for Product SKU ID: {}", ratingInputDto.getProductSkuId());
        return;
      }
      double bayesianRating = getBayesianRating(ratingInputDto, currentRating, currentUsersCount);
      Update update = new Update().set("rating", bayesianRating);
      mongoTemplate.findAndModify(query, update, ProductsEntity.class);
      log.info("Successfully updated rating for Product SKU ID: {} to {}",
          ratingInputDto.getProductSkuId(),
          bayesianRating);
    } catch (Exception e) {
      log.error("An error occurred while updating the rating for Product SKU ID: {}: {}",
          ratingInputDto.getProductSkuId(),
          e.getMessage());
    }
  }

  //FIND ALL PRODUCTS BY CATEGORY ID
  @Override
  public List<ProductResponseDto> getProductsByCategory(String categoryId) {
    Category category = graphQLResolver.getCategory(categoryId);
    if (category == null) {
      throw new IllegalArgumentException("Category not found with ID: " + categoryId);
    }
    List<String> productIds = category.getProductIds();
    List<ProductsEntity> productsEntities = findByProductSkuIds(productIds);
    return productsEntities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public Boolean updateProductInformation(ProductsEntity productsEntity) {
    log.info("Updating product information for SKU ID: {}", productsEntity.getProductSkuId());
    try {
      ProductsEntity existingProduct = productsRepository.findByProductSkuId(productsEntity.getProductSkuId());
      if (existingProduct != null) {
        Query query = new Query();
        query.addCriteria(Criteria.where("productSkuId").is(productsEntity.getProductSkuId()));

        Update update = new Update();

        if (productsEntity.getProductName() != null) {
          update.set("productName", productsEntity.getProductName());
        }
        if (productsEntity.getProductDescription() != null) {
          update.set("productDescription", productsEntity.getProductDescription());
        }
        if (productsEntity.getProductImages() != null) {
          update.set("productImages", productsEntity.getProductImages());
        }
        if (productsEntity.getCategoryId() != null) {
          update.set("categoryId", productsEntity.getCategoryId());
        }
        if (productsEntity.getMerchantId() != null) {
          update.set("merchantId", productsEntity.getMerchantId());
        }
        if (productsEntity.getPrice() != null) {
          update.set("price", productsEntity.getPrice());
        }
        if (productsEntity.getStock() >= Integer.MIN_VALUE) {
          update.set("stock", productsEntity.getStock());
        }
        if (productsEntity.getBrand() != null) {
          update.set("brand", productsEntity.getBrand());
        }
        update.set("dateModified", new Date());
        mongoTemplate.findAndModify(query, update, ProductsEntity.class);
        log.info("Product information updated successfully for SKU ID: {}", productsEntity.getProductSkuId());
        return true;
      } else {
        log.error("Product with SKU ID: {} not found", productsEntity.getProductSkuId());
        return false;
      }
    } catch (Exception e) {
      log.error("An error occurred while updating product information: {}", e.getMessage());
      return false;
    }
  }

  private List<ProductsEntity> findByProductSkuIds(List<String> productIds) {
    Query query = new Query(Criteria.where("productSkuId").in(productIds));
    return mongoTemplate.find(query, ProductsEntity.class);
  }

  private static double getBayesianRating(RatingInputDto ratingInputDto, int currentRating, int currentUsersCount) {
    int newRatingValue = ratingInputDto.getRatingValue();
    // Weighted average with decay
    double decayFactor = 0.9; // Older ratings have less weight
    double weightedCurrentRating = currentRating * Math.pow(decayFactor, currentUsersCount);
    double newWeightedRatingSum = weightedCurrentRating + newRatingValue;
    // Bayesian average
    int minimumRatings = 10; // Minimum number of ratings for a more reliable average
    double meanRating = 3.0; // Assumed mean rating (prior belief)
    double bayesianRating =
        ((minimumRatings * meanRating) + newWeightedRatingSum) / (minimumRatings + currentUsersCount + 1);
    // Ensure the new rating is at least 1
    bayesianRating = Math.max(bayesianRating, 1.0);
    return bayesianRating;
  }

  // Find ALL PRODUCTS IN PAGINATED CALL
  @Override
  public Page<ProductResponseDto> getAllProducts(Integer page, Integer size) {
    int validPage = (page == null || page < 0) ? DEFAULT_PAGE : page;
    int validSize = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
    Pageable pageable = PageRequest.of(validPage, validSize);
    Page<ProductsEntity> productEntities = productsRepository.findAll(pageable);
    return productEntities.map(this::convertToDto);
  }

  private ProductResponseDto convertToDto(ProductsEntity entity) {
    ProductResponseDto productResponseDto = new ProductResponseDto();
    BeanUtils.copyProperties(entity, productResponseDto);
    List<ExternalMerchantDto> merchants =
        CompletableFuture.supplyAsync(() -> feignInterface.getMerchantDetailList(entity.getMerchantId())).join();
    productResponseDto.setMerchants(merchants);
    HashMap<ExternalMerchantDto, Double> priceMap = new HashMap<>();
    HashMap<String, Double> prices = entity.getPrice();
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

}
