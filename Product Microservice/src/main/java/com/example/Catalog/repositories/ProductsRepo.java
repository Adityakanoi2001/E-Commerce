package com.example.Catalog.repositories;

import com.example.Catalog.dto.ProductsDto;
import com.example.Catalog.entities.ProductsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductsRepo extends MongoRepository<ProductsEntity, String> {
    public List<ProductsDto> findByProductName(String productName);
    public List<ProductsDto> findByStock(Integer stock);
}
