package com.example.Catalog.controller;

import com.example.Catalog.dto.ProductInputDto;
import com.example.Catalog.dto.StockDecreaseDto;
import com.example.Catalog.dto.StockStatus;
import com.example.Catalog.dto.StockUpdateDto;
import com.example.Catalog.entities.ProductsEntity;
import com.example.Catalog.helper.ExternalMerchantControllerApiPaths;
import com.example.Catalog.services.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = ExternalMerchantControllerApiPaths.BASE_PATH)
public class ExternalMerchantController {

  @Autowired
  private ProductsService productsService;

  @GetMapping(value = "/getByCategoryMerchant/{merchantId}")

  public ResponseEntity<List<ProductInputDto>> productsListByMerchant(@PathVariable("merchantId") String merchantId) {

    List<ProductInputDto> ProductByCategoryMerchant = new ArrayList<>();
    for (ProductsEntity productsEntity : productsRepo.findAll()) {
      if (productsEntity.getMerchantId().equals(merchantId)) {
        ProductInputDto productsDto = new ProductInputDto();
        BeanUtils.copyProperties(productsEntity, productsDto);
        ProductByCategoryMerchant.add(productsDto);
      }
    }
    if (!ProductByCategoryMerchant.isEmpty()) {

      ListOfProductItems listOfProductItems = new ListOfProductItems();
      listOfProductItems.setProductsDtoList(ProductByCategoryMerchant);

      return new ResponseEntity(listOfProductItems, HttpStatus.OK);
    } else {
      return new ResponseEntity("merchat id is not present", HttpStatus.OK);
    }


  }

  @PostMapping(value = "/addStock")
  public ResponseEntity<StockStatus> callOtherServer(@RequestBody StockUpdateDto stockUpdateDto) {

    productsService.updateStockValue(stockUpdateDto);
    return new ResponseEntity("Stock Added ", HttpStatus.OK);
  }


  @PostMapping(value = "/updateStock")
  public ResponseEntity<StockStatus> updatestocknew(@RequestBody StockUpdateDto stockUpdateDto) {

    productsService.increaseStock(stockUpdateDto);
    return new ResponseEntity(" Stock Updated", HttpStatus.OK);
  }

  @PostMapping(value = "/decreaseStock")
  public ResponseEntity<StockStatus> decreasedStock(@RequestBody StockDecreaseDto stockDecreaseDto) {

    return new ResponseEntity(productsService.decreaseStock(stockDecreaseDto), HttpStatus.OK);
  }

  @GetMapping("/getStock/{productId}")
  public ResponseEntity<Stock> getStock(@PathVariable("productId") String productId) {
    Stock stock = new Stock();
    stock.setStock(productsService.getStock(productId));
    return new ResponseEntity(stock, HttpStatus.OK);
  }

}
