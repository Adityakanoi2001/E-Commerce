package com.example.MerchantMongo.repo;

import com.example.MerchantMongo.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRep extends MongoRepository<ProductEntity,String> {
}
