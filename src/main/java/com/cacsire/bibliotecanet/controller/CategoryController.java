package com.cacsire.bibliotecanet.controller;

import com.cacsire.bibliotecanet.model.CategoryEntity;
import com.cacsire.bibliotecanet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<Collection<CategoryEntity>> getAllCategories() {
        try {
            Collection<CategoryEntity> categories = categoryService.findAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/all/user")
    public ResponseEntity<Collection<CategoryEntity>> getAllCategoriesUser() {
        try {
            Collection<CategoryEntity> categories = categoryService.findAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CategoryEntity category) {
        try {
            ResponseEntity<String> response = categoryService.saveCategory(category);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return ResponseEntity.ok("Categoría creada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría");
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        try {
            return categoryService.deleteCategory(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la categorías");
        }
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryEntity categoryUpdate) {
        try {
            ResponseEntity<?> response = categoryService.updateCategory(categoryId, categoryUpdate);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Categoría actualizada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la categoría");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la categoría");
        }
    }

}
