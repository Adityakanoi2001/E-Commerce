package com.example.MerchantMongo.services;

import com.example.MerchantMongo.dto.StockUpdateDto;
import com.example.MerchantMongo.entity.Stock;


public interface StockService {

    public Stock stockadd(StockUpdateDto stockUpdateDto);
    //public int updateStock(String merchnatId,String productId);
}
