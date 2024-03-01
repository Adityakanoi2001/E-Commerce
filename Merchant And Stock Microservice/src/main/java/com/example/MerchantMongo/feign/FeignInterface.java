package com.example.MerchantMongo.feign;


import com.example.MerchantMongo.dto.StockUpdateDto;
import com.example.MerchantMongo.entity.ProductEntity;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@FeignClient(url = "http://10.20.2.120:8095/products" ,value = "feingdemo")
public interface FeignInterface {

//   @GetMapping(value = "/productList")
//    public List<ProductEntity> productsLists();

   @GetMapping(value = "/updateStock")
    public String demo (@RequestBody StockUpdateDto stockUpdateDto);

   @GetMapping(value = "/increaseStock")
   public String demoupdate (@RequestBody StockUpdateDto stockUpdateDto);

}
