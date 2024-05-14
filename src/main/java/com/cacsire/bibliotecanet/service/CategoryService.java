package com.cacsire.bibliotecanet.service;

import com.cacsire.bibliotecanet.model.CategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface CategoryService {
    ResponseEntity<String> saveCategory(CategoryEntity category);

    ResponseEntity<?> deleteCategory(Long categoryId);

    ResponseEntity<?> updateCategory(Long categoryId, CategoryEntity categoryUpdate);

    Collection<CategoryEntity> findAll();
}
