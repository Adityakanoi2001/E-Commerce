package com.example.productsMongo.repositories;

import com.example.productsMongo.ProductsDto.ProductsDto;
import com.example.productsMongo.entities.ProductsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductsRepo extends MongoRepository<ProductsEntity, String> {
    public List<ProductsDto> findByProductName(String productName);
    public List<ProductsDto> findByStock(Integer stock);
}
