package com.example.Catalog.services.serviceImpls;

import com.example.Catalog.entities.Category;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
  private final Map<String, Category> categoryMap = new HashMap<>();

  public Category createCategory(String categoryId, String categoryName) {
    if (categoryId == null || categoryId.isEmpty() || categoryName == null || categoryName.isEmpty()) {
      return null;
    }
    if (categoryMap.containsKey(categoryId)) {
      return null;
    }
    Category category = new Category(categoryId, categoryName, new ArrayList<>());
    categoryMap.put(categoryId, category);
    return category;
  }

  public Category addProductToCategory(String categoryId, String productId) {
    Category category = categoryMap.get(categoryId);
    if (category != null) {
      category.getProductIds().add(productId);
    }
    return category;
  }

  public Category getCategoryById(String categoryId) {
    return categoryMap.get(categoryId);
  }
  public List<Category> getAllCategories() {
    return new ArrayList<>(categoryMap.values());
  }
}
