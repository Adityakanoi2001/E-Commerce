package com.example.MerchantMongo.controller;


import com.example.MerchantMongo.dto.StockUpdateDto;
import com.example.MerchantMongo.entity.Stock;
import com.example.MerchantMongo.feign.FeignInterface;
import com.example.MerchantMongo.services.StockService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchantStock")

public class StockController
{
    @Autowired
    FeignInterface feignInterface;

    @Autowired
    StockService stockService;


    @PostMapping(value = "addStock")
    public ResponseEntity<Stock> addtoStock (@RequestBody StockUpdateDto stockUpdateDto)
    {
        stockService.stockadd(stockUpdateDto);
        return new ResponseEntity("Stock Updated",HttpStatus.OK);
    }

    @PostMapping(value = "/updateStock")
    public ResponseEntity<String> productsList (@RequestBody StockUpdateDto stockUpdateDto)
    {
        String stock = feignInterface.demo(stockUpdateDto);
        return new ResponseEntity(stock,HttpStatus.OK);
    }

    @PostMapping(value = "/addStock")
    public ResponseEntity<String> updatestocknew (@RequestBody StockUpdateDto stockUpdateDto)
    {
        String stock = feignInterface.demoupdate(stockUpdateDto);
        return new ResponseEntity(stock,HttpStatus.OK);
    }
}


//("productId") String productId, @PathVariable("merchantId") String merchantId, @PathVariable("stock") int stock)