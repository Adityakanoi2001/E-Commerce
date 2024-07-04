package com.example.Catalog.repositories;

import com.example.Catalog.entities.ProductsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends MongoRepository<ProductsEntity, String> {
    ProductsEntity findByProductSkuId(String productSkuId);
}
