package com.example.CartEcommerce.controller;


import com.example.CartEcommerce.dto.*;
import com.example.CartEcommerce.entites.CartEntity;
import com.example.CartEcommerce.entites.ProductsEntity;
import com.example.CartEcommerce.feign.FeignInterface;
import com.example.CartEcommerce.repositories.CartRepo;
import com.example.CartEcommerce.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")

public class CartController {



    @Autowired
    CartService cartService;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    FeignInterface feignInterface;
    @Autowired
    MongoTemplate mongoTemplate;



    @PostMapping (value = "/add")
    public ResponseEntity<CartStatus> addProducts(@RequestBody CartDto cartDto) {

       CartEntity cartEntity=new CartEntity();
       List<ProductsEntity> productsEntityList = cartService.getProductDetailsBYProductId(cartDto.getProductId());

        if(!cartRepo.existsById(cartDto.getUserId()))
        {
             productsEntityList.get(0).setQuantity(1);
             cartEntity.setUserId(cartDto.getUserId());
             cartEntity.setProductsEntities(productsEntityList);
             cartEntity.setTotalCost(productsEntityList.get(0).getPrice());
             cartService.addEntity(cartEntity);
        }

        else
            {
                Query query = new Query();
                Update update = new Update();
                query.addCriteria(Criteria.where("_id").is(cartDto.getUserId()).and("productsEntities._id").is(cartDto.getProductId()));
                List<CartEntity> cartEntityList = mongoTemplate.find(query, CartEntity.class);

                if(cartEntityList.size()==0)
                {
                    Query query1 = new Query();
                    query1.addCriteria(Criteria.where("_id").is(cartDto.getUserId()));
                    List<CartEntity> orderEntities = mongoTemplate.find(query1, CartEntity.class);
                    double price = orderEntities.get(0).getTotalCost() + productsEntityList.get(0).getPrice();
                    update.set("totalCost", price);
                    mongoTemplate.findAndModify(query1, update, CartEntity.class);
                    productsEntityList.get(0).setQuantity(1);
                    update.addToSet("productsEntities", productsEntityList.get(0));
                    mongoTemplate.findAndModify(query1, update, CartEntity.class);
                }
                else
                    {
                        int qunatity = 1;
                        for(ProductsEntity productsEntity:cartEntityList.get(0).getProductsEntities())
                        {
                            if(productsEntity.getProductId().equals(cartDto.getProductId()))
                            {
                                qunatity =  productsEntity.getQuantity();
                                break;
                            }
                        }

                        update.set("productsEntities.$.quantity", qunatity + 1);
                        mongoTemplate.findAndModify(query, update, CartEntity.class);

                        Query query2 = new Query();
                        query2.addCriteria(Criteria.where("_id").is(cartDto.getUserId()));
                        List<CartEntity> orderEntities = mongoTemplate.find(query2, CartEntity.class);
                        double price = orderEntities.get(0).getTotalCost() + productsEntityList.get(0).getPrice();
                        Update update1 = new Update();
                        update1.set("totalCost", price);
                        mongoTemplate.findAndModify(query2, update1, CartEntity.class);
                    }

            }

        CartStatus cartStatus = new CartStatus();
        cartStatus.setStatus("cart is added  with id : " + cartDto.getProductId());
        return new ResponseEntity(cartStatus, HttpStatus.CREATED);
    }





    @GetMapping (value = "/CartProduct/{id}")
    public ResponseEntity<List<ProductsEntity>> CartProducts(@PathVariable ("id") String id)
    {
        return new ResponseEntity(cartService.getProductDetailsBYProductId(id),HttpStatus.OK);
    }


    @GetMapping (value = "CartProductsById/{id}")
    public ResponseEntity<CartReturnDto> CartProductsById(@PathVariable ("id") String id)
    {
       return new ResponseEntity(cartService.getProductDetailsByUserId(id),HttpStatus.OK);

    }

    @DeleteMapping(value = "deleteCartProduct/{userId}/{productId}")
    public ResponseEntity<CartStatus> deleteCartProduct(@PathVariable ("userId") String userid, @PathVariable ("productId") String productid)
    {
        List<ProductsEntity> productsEntityList = cartService.getProductDetailsBYProductId(productid);

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("_id").is(userid));
        List<CartEntity> orderEntities = mongoTemplate.find(query1,CartEntity.class);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userid).and("productsEntities._id").is(productid));
        List<CartEntity> cartEntityList = mongoTemplate.find(query, CartEntity.class);
        int quantity=1;
        if(cartEntityList.size()!=0) {
            for (ProductsEntity productsEntity : cartEntityList.get(0).getProductsEntities()) {
                if (productsEntity.getProductId().equals(productid)) {
                    quantity = productsEntity.getQuantity();
                    break;
                }
            }
        }
        double price =0;
        if(orderEntities.size()!=0) {
            price = orderEntities.get(0).getTotalCost() - productsEntityList.get(0).getPrice() * quantity;
            Update update = new Update();
            update.set("totalCost", price);
            mongoTemplate.findAndModify(query1, update, CartEntity.class);
        }
            cartService.deleteBy(userid, productid);
            CartStatus cartStatus = new CartStatus();
            cartStatus.setStatus("Product delted");
            cartStatus.setTotalCost(price);
        return  new ResponseEntity(cartStatus,HttpStatus.OK);
    }

    @PostMapping(value = "/addAllToOrder/{userId}")
    public ResponseEntity<CartStatus> addAllToOrder (@PathVariable("userId") String userId)
    {

        return  new ResponseEntity(cartService.allAllToCart(userId),HttpStatus.OK);
    }






    @PostMapping(value = "cartStockCheck/{userId}/{productId}/{quantity}")
    public ResponseEntity<StockCheckDto> cartStockCheck(@PathVariable("userId") String userId,@PathVariable("productId") String productId,@PathVariable("quantity") int quantity)
    {
        Stock currentStockValue= feignInterface.getStock(productId);

        StockCheckDto stockCheckDto=new StockCheckDto();

        if(currentStockValue.getStock() < quantity)
        {
            stockCheckDto.setStatus("50");

        }
        else
        {
            stockCheckDto.setStatus("100");


        }

        stockCheckDto.setCurrentStock(currentStockValue.getStock());
        return new ResponseEntity(stockCheckDto,HttpStatus.OK);
    }
    @PostMapping(value = "cartStockUpdate/{userId}/{productId}/{quantity}")
    public ResponseEntity<String> cartStockUpdate(@PathVariable("userId") String userId,@PathVariable("productId") String productId,@PathVariable("quantity") int quantity)
    {
        Stock currentStockValue= feignInterface.getStock(productId);

        StockCheckDto stockCheckDto=new StockCheckDto();
        String s="";

        if(currentStockValue.getStock() < quantity)
        {
            s= "NOt Possible";
        }
        else
        {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(userId).and("productsEntities._id").is(productId));
            List<CartEntity> cartEntityList = mongoTemplate.find(query,CartEntity.class);
//            if(cartEntityList.size()!=0)
//            {
//                for(ProductsEntity productsEntity:cartEntityList.get(0).getProductsEntities()){
//                    if(productsEntity.getProductId().equals(productId)){
//
//                    }
//                }
//            }
            List<ProductsEntity> productsEntityList = cartService.getProductDetailsBYProductId(productId);
            double price = cartEntityList.get(0).getTotalCost() + productsEntityList.get(0).getPrice();
            Update update = new Update();
            update.set("productsEntities.$.quantity",quantity);
            update.set("totalCost",price);
            mongoTemplate.findAndModify(query,update,CartEntity.class);
            s = "Updated";

        }

        return new ResponseEntity(s,HttpStatus.OK);
    }
    @PostMapping(value = "cartStockDecrement/{userId}/{productId}/{quantity}")
    public ResponseEntity<String> cartStockDecrement(@PathVariable("userId") String userId,@PathVariable("productId") String productId,@PathVariable("quantity") int quantity)
    {
        Stock currentStockValue= feignInterface.getStock(productId);

        StockCheckDto stockCheckDto=new StockCheckDto();
        String s="";
//        int quant = 1;

        if(quantity<=0)
        {
            s= "NOt Possible";
        }
        else
        {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(userId).and("productsEntities._id").is(productId));
            List<CartEntity> cartEntityList = mongoTemplate.find(query,CartEntity.class);

//            if(cartEntityList.size()!=0)
//            {
//                for(ProductsEntity productsEntity:cartEntityList.get(0).getProductsEntities()){
//                    if(productsEntity.getProductId().equals(productId)){
//                        quant = productsEntity.getQuantity()-1;
//                    }
//                }
//            }

            List<ProductsEntity> productsEntityList = cartService.getProductDetailsBYProductId(productId);
            double price = cartEntityList.get(0).getTotalCost() - productsEntityList.get(0).getPrice();
            Update update = new Update();
            update.set("productsEntities.$.quantity",quantity);
            update.set("totalCost",price);
            mongoTemplate.findAndModify(query,update,CartEntity.class);
            s = "Updated";

        }

        return new ResponseEntity(s,HttpStatus.OK);
    }
    @GetMapping(value = "/getStockFromCart/{userId}/{productId}")
    public ResponseEntity<Stock> getStockFromCart(@PathVariable("userId") String userId, @PathVariable("productId") String productId){

        return new ResponseEntity(cartService.noOfQunatity(userId,productId),HttpStatus.OK);
    }



}
