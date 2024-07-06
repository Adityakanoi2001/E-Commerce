package com.example.Catalog.helper;

public interface ExternalSalesControllerApiPaths {
  String BASE_PATH = Constants.CONTEXT_PATH + "SalesCatalogController";
  String INCREASE_PRODUCT_SALE_COUNT = "/increaseBuyersCount/{productSkuId}";
}
