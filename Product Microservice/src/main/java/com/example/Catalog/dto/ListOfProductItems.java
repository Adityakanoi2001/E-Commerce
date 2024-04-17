package com.example.Catalog.dto;

import java.util.List;

public class ListOfProductItems {


    private List<ProductsDto> productsDtoList;

    public List<ProductsDto> getProductsDtoList() {
        return productsDtoList;
    }

    public void setProductsDtoList(List<ProductsDto> productsDtoList) {
        this.productsDtoList = productsDtoList;
    }
}
