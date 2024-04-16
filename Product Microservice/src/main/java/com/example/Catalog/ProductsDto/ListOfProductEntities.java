package com.example.Catalog.ProductsDto;

import com.example.Catalog.entities.ProductsEntity;

import java.util.List;

public class ListOfProductEntities {


    private List<ProductsEntity> productsEntities;

    public List<ProductsEntity> getProductsEntities() {
        return productsEntities;
    }

    public void setProductsEntities(List<ProductsEntity> productsEntities) {
        this.productsEntities = productsEntities;
    }


}
