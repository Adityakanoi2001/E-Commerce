package com.example.productsMongo.services.impl;


import com.example.productsMongo.ProductsDto.*;
import com.example.productsMongo.entities.ProductsEntity;
import com.example.productsMongo.entities.Reviews;
import com.example.productsMongo.repositories.ProductsRepo;
import com.example.productsMongo.services.ProductsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class ProductsServiceImpl implements ProductsService
{


    @Autowired
    ProductsRepo productsRepo;

    @Autowired
    MongoTemplate mongoTemplate;


    public void addProducts(ProductsEntity currentproduct)
    {
        productsRepo.save(currentproduct);
    }

    @Override
    public boolean deleteProduct(String id)
    {
        if(productsRepo.existsById(id))
        {
            productsRepo.deleteById(id);
            return true;
        }
        return false;
    }

//
    @Override
    public void updateProduct(ProductsEntity currentproduct)
    {
        productsRepo.save(currentproduct);
    }

    //GET ALL THE PRODUCTS
    @Override
        public Iterable<ProductsEntity> productsList()
    {
            return productsRepo.findAll();
        }


        // GET PRODUCTS DETAILS BY PRODUCT NAME REGEX
    @Override
    public ListOfProductEntities getAllProductsBySearchTerm(String productName)
    {

        ListOfProductEntities listOfProductEntities = new ListOfProductEntities();
        Pattern regs = Pattern.compile(productName, Pattern.CASE_INSENSITIVE);

        Query query = new Query();
        query.addCriteria(Criteria.where("productName").regex(regs));
        List<ProductsEntity> productsEntities = mongoTemplate.find(query,ProductsEntity.class);
        listOfProductEntities.setProductsEntities(productsEntities);
        return listOfProductEntities;
    }

    //Update Stock - MERCHNAT USER
    @Override
    public StockStatus updateStockValue(StockUpdateDto stockUpdateDto) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(stockUpdateDto.getProductId()).and("merchantId").is(stockUpdateDto.getMerchantId()));
        Update update = new Update();
        update.set("stock",stockUpdateDto.getStock());
        mongoTemplate.findAndModify(query,update,ProductsEntity.class);
        return null;
    }

    //INCREASE STOCK MERCHNAT USER
    @Override
    public StockStatus increaseStock(StockUpdateDto stockUpdateDto) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(stockUpdateDto.getProductId()).and("merchantId").is(stockUpdateDto.getMerchantId()));

        List<ProductsEntity> productsEntityList = mongoTemplate.find(query,ProductsEntity.class);
        System.out.print(productsEntityList);

        ProductsEntity productsEntity = productsEntityList.get(0);
        int stock = stockUpdateDto.getStock()+productsEntity.getStock();
        Update update = new Update();
        update.set("productsEntities.stock",stock);
        mongoTemplate.findAndModify(query,update,ProductsEntity.class);

        return null;
    }

    // DECREASE STOCK - INTERNRANL CALLING WHEN ORDER IS PLACED FINGED IN ORDER CONTROLLLER
    @Override
    public StockStatus decreaseStock(StockDecreaseDto stockDecreaseDto)
    {
        Query query = new Query();

        query.addCriteria(Criteria.where("_id").is(stockDecreaseDto.getProductId()));

        List<ProductsEntity> productsEntityList = mongoTemplate.find(query,ProductsEntity.class);

        ProductsEntity productsEntity = productsEntityList.get(0);

        int stock = productsEntity.getStock() - stockDecreaseDto.getQuantity();

        Update update = new Update();

        update.set("stock",stock);

        mongoTemplate.findAndModify(query,update,ProductsEntity.class);

        StockStatus stockStatus = new StockStatus();
        stockStatus.setStatus("Updated");

        return stockStatus;
    }

    //
    @Override
    public ListOfProductEntities findByProductName(String merchantId, String productName)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("productName").is(productName).and("merchantId").ne(merchantId));
        List <ProductsEntity> li= mongoTemplate.find(query,ProductsEntity.class);
        ListOfProductEntities listOfProductEntities = new ListOfProductEntities();
        listOfProductEntities.setProductsEntities(li);
        return listOfProductEntities;
    }

    @Override
    public Integer getStock(String productId)
    {
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(productId));
        List<ProductsEntity> productsEntitiesList=mongoTemplate.find(query,ProductsEntity.class);
        Integer currentStock=productsEntitiesList.get(0).getStock();
        return currentStock;
    }

    //RATING THE PRODUCT
    @Override
    public int getRating(String productId,Integer currentRatingNew)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(productId));
        List<ProductsEntity> productsEntityList = mongoTemplate.find(query,ProductsEntity.class);
        int currentRating = productsEntityList.get(0).getRating();
        Integer currentUsersCount=productsEntityList.get(0).getNoOfBuyers();

        int newRating= (currentRating+currentRatingNew) / currentUsersCount;
        if(newRating<1)
        {
            newRating = 1;
            Update update=new Update();
            update.set("rating",newRating);
            mongoTemplate.findAndModify(query,update,ProductsEntity.class);
        }
        Update update1=new Update();
        update1.set("rating",newRating);
        mongoTemplate.findAndModify(query,update1,ProductsEntity.class);
        return newRating;
    }


    // INCREASE NO OF BUYERS - INTERNAL CALLING WHEN ORDER IS PLACED
    @Override
    public void countOfBuyers(String productId)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(productId));
        List<ProductsEntity> productsEntityList= mongoTemplate.find(query,ProductsEntity.class);
        Integer currentNoOfBuyers= productsEntityList.get(0).getNoOfBuyers()+1;
        Update update = new Update();
        update.set("noOfBuyers",currentNoOfBuyers);
        mongoTemplate.findAndModify(query,update,ProductsEntity.class);
        System.out.println(currentNoOfBuyers);
    }

    //REVIEW API TO ADD REVIEW
    @Override
    public String reviews(String review, String userId, String productId)
    {
        Query query = new Query();
        Reviews reviews = new Reviews();
        query.addCriteria(Criteria.where("_id").is(productId));
        //List<ProductsEntity> productsEntityList = mongoTemplate.find(query,ProductsEntity.class); No USEAGE
        reviews.setUserName(userId);
        reviews.setReviewContent(review);
        Update update = new Update();
        update.addToSet("review",reviews);
        mongoTemplate.findAndModify(query,update,ProductsEntity.class);
        return "Review Added";
    }


}
