package com.example.productsMongo.ProductsDto;

import com.example.productsMongo.entities.ProductsEntity;

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
