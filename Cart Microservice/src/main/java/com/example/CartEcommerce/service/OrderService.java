package com.example.CartEcommerce.service;

import com.example.CartEcommerce.dto.EmailService;
import com.example.CartEcommerce.dto.OrderDto;
import com.example.CartEcommerce.dto.OrderReturnDTO;
import com.example.CartEcommerce.entites.CartEntity;
import com.example.CartEcommerce.entites.OrderEntity;
import com.example.CartEcommerce.entites.ProductsEntity;

import java.util.List;

public interface OrderService {
    public void addToOrder();
    public void addEntity(OrderEntity orderEntity);
    public List<ProductsEntity> getProductDetailsByProductId(String id);
//    public List<ProductsEntity> findById(String id);
    public OrderReturnDTO getAllOrders(String userId);
    public void AddingAllProductsToOrder(CartEntity cartEntity);

//    public String sendMail(OrderDto orderDto);
}
