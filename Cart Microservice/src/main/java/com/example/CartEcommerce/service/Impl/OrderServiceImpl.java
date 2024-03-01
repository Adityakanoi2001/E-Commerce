package com.example.CartEcommerce.service.Impl;

import com.example.CartEcommerce.dto.*;
import com.example.CartEcommerce.entites.CartEntity;
import com.example.CartEcommerce.entites.OrderEntity;
import com.example.CartEcommerce.entites.ProductsEntity;
import com.example.CartEcommerce.feign.FeignInterface;
import com.example.CartEcommerce.feign.FeignInterfaceForEmail;
import com.example.CartEcommerce.repositories.OrderRepo;
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

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    FeignInterface feignInterface;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    FeignInterfaceForEmail feignInterfaceForEmail;


    @Override
    public void addToOrder() {
    }

    @Override
    public void addEntity(OrderEntity orderEntity) {
        orderRepo.save(orderEntity);

    }


    public List<ProductsEntity> getProductDetailsByProductId(String id){

        List<ProductsEntity> l=new ArrayList<ProductsEntity>();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(id);
        Iterable<ProductsEntity> productsIterable=feignInterface.getByProductId(id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        for(ProductsEntity s:productsIterable){

            l.add(s);
        }
        return  l;
    }

    @Override
    public OrderReturnDTO getAllOrders(String userId) {
        Query query = new Query();
        OrderReturnDTO orderReturnDTO = new OrderReturnDTO();
        query.addCriteria(Criteria.where("_id").is(userId));
        List<OrderEntity> orderReturnDTOList = mongoTemplate.find(query,OrderEntity.class);

        if(orderReturnDTOList.size()!=0)
        {
            BeanUtils.copyProperties(orderReturnDTOList.get(0), orderReturnDTO);
        }

        return  orderReturnDTO;
    }

    @Override
    public void AddingAllProductsToOrder(CartEntity cartEntity) {

        List<ProductsEntity> productsEntityList = cartEntity.getProductsEntities();
//        System.out.println(productsEntityList.size());
        Double totalCost = cartEntity.getTotalCost();
        OrderEntity orderEntity=new OrderEntity();
        if(!orderRepo.existsById(cartEntity.getUserId())){
            orderEntity.setUserId(cartEntity.getUserId());
            orderEntity.setProductsEntities(null);
            orderEntity.setTotalCost(0);
            orderRepo.save(orderEntity);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(cartEntity.getUserId()));
        Update update;
        for (ProductsEntity productsEntity : productsEntityList) {
            update = new Update();
            update.addToSet("productsEntities", productsEntity);
            mongoTemplate.findAndModify(query, update, OrderEntity.class);
            StockDecreaseDto stockDecreaseDto = new StockDecreaseDto();
            stockDecreaseDto.setProductId(productsEntity.getProductId());
            stockDecreaseDto.setQuantity(productsEntity.getQuantity());

            feignInterface.decreasedStock(stockDecreaseDto);

//         calling function in products to increase the buyers count in product collection
            feignInterface.increaseBuyersCount(productsEntity.getProductId());
        }
        List<OrderEntity> orderEntityList = mongoTemplate.find(query, OrderEntity.class);

        double   price = orderEntityList.get(0).getTotalCost();
        double totalprice = totalCost + price;
        Update update1 = new Update();
        update1.set("totalCost", totalprice);
        mongoTemplate.findAndModify(query, update1, OrderEntity.class);

    }


//
//    @Override
//    public String sendMail(OrderDto orderDto)
//    {
//        EmailService emailService1 = new EmailService();
//        emailService1.setRecipient(orderDto.getUserId());
//
//        emailService1.setMsgBody("");
//        emailService1.setSubject("ORDER SUMMARY MAIL");
//        feignInterfaceForEmail.callEmailServer(emailService1);
//        return "Send Successfull";
//    }
}
