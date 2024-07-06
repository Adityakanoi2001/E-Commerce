package com.example.Catalog.controller;

import com.example.Catalog.helper.ExternalMerchantControllerApiPaths;
import com.example.Catalog.services.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = ExternalMerchantControllerApiPaths.BASE_PATH)
public class ExternalMerchantController {

  @Autowired
  private ProductsService productsService;

}
