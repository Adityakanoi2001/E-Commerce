package com.example.CartEcommerce.repositories;

import com.example.CartEcommerce.entites.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends MongoRepository<CartEntity,String> {

}
