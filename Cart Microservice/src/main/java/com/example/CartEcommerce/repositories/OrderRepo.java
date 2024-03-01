package com.example.CartEcommerce.repositories;

import com.example.CartEcommerce.entites.CartEntity;
import com.example.CartEcommerce.entites.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends MongoRepository<OrderEntity,String> {

}
