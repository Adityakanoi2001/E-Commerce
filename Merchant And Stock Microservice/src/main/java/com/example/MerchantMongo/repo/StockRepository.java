package com.example.MerchantMongo.repo;


import com.example.MerchantMongo.entity.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository  extends MongoRepository<Stock,String> {


}
