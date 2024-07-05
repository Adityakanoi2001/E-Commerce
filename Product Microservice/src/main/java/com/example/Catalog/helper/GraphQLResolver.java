package com.example.Catalog.helper;

import com.example.Catalog.entities.Category;
import com.example.Catalog.services.serviceImpls.CategoryService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

  @Autowired
  private CategoryService categoryService;

  public List<Category> getAllCategory() {
    return categoryService.getAllCategories();
  }

  public Boolean createCategory(String categoryId, String categoryName) {
    return categoryService.createCategory(categoryId, categoryName);
  }

  public Category addProductToCategory(String categoryId, String productId) {
    return categoryService.addProductToCategory(categoryId, productId);
  }
}
