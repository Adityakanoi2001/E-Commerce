package com.example.Catalog.controller;

import com.example.Catalog.helper.ExternalMerchantControllerApiPaths;
import com.example.Catalog.helper.ExternalSalesControllerApiPaths;
import com.example.Catalog.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = ExternalMerchantControllerApiPaths.BASE_PATH)
public class ExternalMerchantController {

  @Autowired
  private ProductsService productsService;

  @Operation(summary = "Increase Product Sale Count",
      description = "Increases the number of buyers for a specific product by its SKU ID")
  @PostMapping(ExternalSalesControllerApiPaths.INCREASE_PRODUCT_SALE_COUNT)
  public ResponseEntity<Void> increaseBuyersCount(@Parameter(description = "SKU ID of the product to update",
      required = true) @PathVariable String productSkuId) {
    try {
      productsService.incrementProductSaleCount(productSkuId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
