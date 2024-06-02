package com.example.Catalog.repositories;

import com.example.Catalog.dto.ProductInputDto;
import com.example.Catalog.entities.ProductsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductsRepository extends MongoRepository<ProductsEntity, String> {
    public List<ProductInputDto> findByProductName(String productName);
    public List<ProductInputDto> findByStock(Integer stock);
    ProductsEntity findByProductSkuId(String productSkuId);
}
