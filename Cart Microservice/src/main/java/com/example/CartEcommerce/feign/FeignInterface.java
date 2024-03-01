package com.example.CartEcommerce.feign;


import com.example.CartEcommerce.dto.Stock;
import com.example.CartEcommerce.dto.StockDecreaseDto;
import com.example.CartEcommerce.dto.StockStatus;
import com.example.CartEcommerce.entites.ProductsEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "productsMongo", url = "http://10.20.2.120:8092/products/")
public interface FeignInterface {

    @GetMapping(value = "getByProductId/{id}")
    List<ProductsEntity> getByProductId(@PathVariable("id") String id);

    @GetMapping("/getStock/{productId}")
    Stock getStock(@PathVariable("productId") String productId);

    @PostMapping("/increaseBuyersCount/{productId}")
    public void increaseBuyersCount(@PathVariable("productId") String productId);



    @PostMapping(value = "/decreaseStock")
    public ResponseEntity<StockStatus> decreasedStock(@RequestBody StockDecreaseDto stockDecreaseDto);


}
