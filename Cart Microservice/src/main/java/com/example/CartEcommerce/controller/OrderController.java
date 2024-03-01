package com.example.CartEcommerce.controller;


import com.example.CartEcommerce.dto.*;
import com.example.CartEcommerce.entites.CartEntity;
import com.example.CartEcommerce.entites.OrderEntity;
import com.example.CartEcommerce.entites.ProductsEntity;
import com.example.CartEcommerce.feign.FeignInterface;
import com.example.CartEcommerce.feign.FeignInterfaceForEmail;
import com.example.CartEcommerce.repositories.CartRepo;
import com.example.CartEcommerce.repositories.OrderRepo;
import com.example.CartEcommerce.service.CartService;
import com.example.CartEcommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orderDetails")
public class OrderController
{

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    FeignInterfaceForEmail feignInterfaceForEmail;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    FeignInterface feignInterface;




    @PostMapping(value = "/addNewOrder")
    public ResponseEntity<CartStatus> addProductsInOrders(@RequestBody OrderDto orderDto)
    {

        OrderEntity orderEntity=new OrderEntity();
        List<ProductsEntity> productsEntityList = orderService.getProductDetailsByProductId(orderDto.getProductId());
        double price =0;
        int decqunatity = orderDto.getQuantity();
        // new user new product one one product
        if(!orderRepo.existsById(orderDto.getUserId()))
        {
            productsEntityList.get(0).setQuantity(orderDto.getQuantity());
            orderEntity.setUserId(orderDto.getUserId());
            orderEntity.setProductsEntities(productsEntityList);
            orderEntity.setTotalCost(productsEntityList.get(0).getPrice()* orderDto.getQuantity());
            orderService.addEntity(orderEntity);
        }

        //this is if the user adds same product more than once
        else{

            productsEntityList.get(0).setQuantity(decqunatity);
            Query query1 = new Query();
            query1.addCriteria(Criteria.where("_id").is(orderDto.getUserId()));
            List<OrderEntity> orderEntities = mongoTemplate.find(query1,OrderEntity.class);
            price = orderEntities.get(0).getTotalCost() + productsEntityList.get(0).getPrice()*decqunatity;
            Update update = new Update();
            update.set("totalCost",price);
            mongoTemplate.findAndModify(query1,update,OrderEntity.class);
            update = new Update();
            update.push("productsEntities",productsEntityList.get(0));
            mongoTemplate.findAndModify(query1,update,OrderEntity.class);

        }




        String productDeatils = "";
        for(int i=0 ; i<productsEntityList.size();i++)
        {
            productDeatils = productDeatils + productsEntityList.get(i).getProductName()+" ";
        }

        EmailService emailService = new EmailService();
        emailService.setRecipient(orderDto.getUserId());
        emailService.setSubject("ORDER DETAILS");
        emailService.setMsgBody(productDeatils);
        feignInterfaceForEmail.callEmailServer(emailService);


//      stock should be decresed in product as order was placed

        StockDecreaseDto stockDecreaseDto = new StockDecreaseDto();
        stockDecreaseDto.setProductId(orderDto.getProductId());
        stockDecreaseDto.setQuantity(decqunatity);
        feignInterface.decreasedStock(stockDecreaseDto);

//       calling function in products to increase the buyers count in product collection
         feignInterface.increaseBuyersCount(orderDto.getProductId());

         CartStatus cartStatus = new CartStatus();
         cartStatus.setTotalCost(price);
         cartStatus.setStatus("Order Successfull");

        return new ResponseEntity(cartStatus, HttpStatus.CREATED);
    }


    @GetMapping (value = "/OrderProduct/{id}")
    public ResponseEntity<List<ProductsEntity>> CartProducts(@PathVariable ("id") String id)
    {
        return new ResponseEntity(orderService.getProductDetailsByProductId(id),HttpStatus.OK);
    }


    @GetMapping(value = "/getAllOrderDetails/{userId}")
    public ResponseEntity<OrderReturnDTO> getAllOrderDetails(@PathVariable("userId") String userId)
    {

        return new ResponseEntity(orderService.getAllOrders(userId),HttpStatus.OK);
    }

//    @GetMapping(value = "/mail")
//    public ResponseEntity<String> sendMail (@RequestBody OrderDto orderDtoformail)
//    {
//        orderService.sendMail(orderDtoformail);
//        return new ResponseEntity("Email Send Successfully",HttpStatus.OK);
//    }


}
