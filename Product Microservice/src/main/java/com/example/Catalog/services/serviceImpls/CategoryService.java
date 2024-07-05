package com.example.Catalog.services.serviceImpls;

import com.example.Catalog.entities.Category;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
  private final Map<String, Category> categoryMap = new HashMap<>();

  public List<Category> getAllCategories() {
    return new ArrayList<>(categoryMap.values());
  }

  public Boolean createCategory(String categoryId, String categoryName) {
    if (categoryId == null || categoryId.isEmpty() || categoryName == null || categoryName.isEmpty()) {
      return false;
    }
    if (categoryMap.containsKey(categoryId)) {
      return false;
    }
    Category category = new Category(categoryId, categoryName, new ArrayList<>());
    categoryMap.put(categoryId, category);
    return true;
  }


  public Category addProductToCategory(String categoryId, String productId) {
    Category category = categoryMap.get(categoryId);
    if (category != null) {
      category.getProductIds().add(productId);
    }
    return category;
  }

  public Category getCategoryById(String categoryId) {
    Category category = categoryMap.get(categoryId);
    return category;
  }
}
