package com.example.CartEcommerce.service.Impl;

import com.example.CartEcommerce.dto.*;
import com.example.CartEcommerce.entites.CartEntity;
import com.example.CartEcommerce.entites.ProductsEntity;
import com.example.CartEcommerce.feign.FeignInterface;
import com.example.CartEcommerce.feign.FeignInterfaceForEmail;
import com.example.CartEcommerce.repositories.CartRepo;
import com.example.CartEcommerce.service.CartService;
import com.example.CartEcommerce.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService {


    @Autowired
    CartRepo cartRepo;

    @Autowired
    OrderService orderService;

    @Autowired
    FeignInterface feignInterface;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    FeignInterfaceForEmail feignInterfaceForEmail;


    @Override
    public void addToCart() {

    }

    @Override
    public void addEntity(CartEntity cartEntity)
    {
        cartRepo.save(cartEntity);

    }


    public List<ProductsEntity> getProductDetailsBYProductId(String id) {

        List<ProductsEntity> l = new ArrayList<ProductsEntity>();
        List<ProductsEntity> productsIterable = feignInterface.getByProductId(id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        for (ProductsEntity s : productsIterable) {

            l.add(s);
        }
        return l;
    }

    @Override
    public CartReturnDto getProductDetailsByUserId(String id)
    {
        Optional<CartEntity> optional = cartRepo.findById(id);
        CartReturnDto cartDto = new CartReturnDto();

        if(optional.isPresent())
        {
            BeanUtils.copyProperties(optional.get(), cartDto);
        }
        return cartDto;
    }

    @Override
    public void deleteBy(String userid, String productid)
    {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userid));
        Update update = new Update();
        update.pull("productsEntities",new Query(Criteria.where("_id").is(productid)));
        mongoTemplate.findAndModify(query,update,CartEntity.class);
    }

    // ADDING ALL PRODUCTS TO ORDERS

    @Override
    public CartStatus allAllToCart(String userid)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userid));
        List<CartEntity> cartEntityList = mongoTemplate.find(query,CartEntity.class);
        System.out.println(cartEntityList);
        if(cartEntityList.size()!=0) {
            orderService.AddingAllProductsToOrder(cartEntityList.get(0));
            cartRepo.deleteById(userid);
        }
        CartStatus cartStatus = new CartStatus();
        cartStatus.setStatus("Successfully Added to Orders !");
        cartStatus.setTotalCost(0);
        return cartStatus;

    }

    @Override
    public Stock noOfQunatity(String userId, String productId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId).and("productsEntities._id").is(productId));
        List<CartEntity> cartEntityList = mongoTemplate.find(query,CartEntity.class);
        Stock stock = new Stock();
        if(cartEntityList.size()!=0)
        {
            for(ProductsEntity productsEntity: cartEntityList.get(0).getProductsEntities()) {
                if(productsEntity.getProductId().equals(productId))
                {
                    stock.setStock(productsEntity.getQuantity());
                }
            }
        }
        else
            {
                stock.setStock(0);
            }
        return stock;
    }

}