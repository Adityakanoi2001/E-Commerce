package com.example.MerchantMongo.services.serviceImpl;



import com.example.MerchantMongo.dto.StockUpdateDto;
import com.example.MerchantMongo.entity.Stock;
import com.example.MerchantMongo.repo.StockRepository;
import com.example.MerchantMongo.services.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.management.Query;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Stock stockadd(StockUpdateDto stockUpdateDto) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockUpdateDto, stock);
        return stockRepository.save(stock);
    }

}
