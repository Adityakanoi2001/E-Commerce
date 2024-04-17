package com.example.Catalog.dto;

import java.util.List;

public class ListOfProductsItem {


    private List<ProductDtos> productsDtoList;

    public List<ProductDtos> getProductsDtoList() {
        return productsDtoList;
    }

    public void setProductsDtoList(List<ProductDtos> productsDtoList) {
        this.productsDtoList = productsDtoList;
    }
}
