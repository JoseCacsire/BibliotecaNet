package com.cacsire.bibliotecanet.service.impl;
import com.cacsire.bibliotecanet.Utils.UtilsLibrary;
import com.cacsire.bibliotecanet.constants.LibraryConstants;
import com.cacsire.bibliotecanet.model.BookEntity;
import com.cacsire.bibliotecanet.model.CategoryEntity;
import com.cacsire.bibliotecanet.repository.BookRepository;
import com.cacsire.bibliotecanet.repository.CategoryRepository;
import com.cacsire.bibliotecanet.service.BookService;
import com.cacsire.bibliotecanet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl  implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookService bookService;


    public static boolean validateCategoryData(CategoryEntity category) {
        return category.getName() != null && !category.getName().isEmpty();
    }

    @Override
    @Transactional
    public ResponseEntity<String> saveCategory(CategoryEntity category) {
        if(!validateCategoryData(category)){
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        CategoryEntity existingCategory = categoryRepository.findByName(category.getName());
        if(existingCategory != null){
            return UtilsLibrary.getResponseEntity("Esta categoria ya existe",HttpStatus.BAD_REQUEST);
        }

        categoryRepository.save(category);
        return UtilsLibrary.getResponseEntity("La categoria fue registrada con exito", HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<?> deleteCategory(Long categoryId) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            log.error("pepe");
            return UtilsLibrary.getResponseEntity("No existe la categoría con id: " + categoryId, HttpStatus.BAD_REQUEST);
        }
        CategoryEntity category = categoryOptional.get();
        Collection<BookEntity> books = bookService.findByCategory(categoryId);
        for (BookEntity book : books) {
            bookService.deleteBook(book.getId());
        }
        categoryRepository.delete(category);

        return UtilsLibrary.getResponseEntity("La categoría fue eliminada con éxito", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> updateCategory(Long categoryId, CategoryEntity categoryUpdate) {
        Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            return UtilsLibrary.getResponseEntity("No existe la categoria con id: "+categoryId,HttpStatus.BAD_REQUEST);
        }

        if(!validateCategoryData(categoryUpdate)){
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        CategoryEntity existingCategory = categoryRepository.findByNameAndIdNot(categoryUpdate.getName(), categoryId);
        if(existingCategory != null){
            return UtilsLibrary.getResponseEntity("Esta categoria ya existe",HttpStatus.BAD_REQUEST);
        }

        category.get().setName(categoryUpdate.getName());
        categoryRepository.save(category.get());
        return UtilsLibrary.getResponseEntity("La categoria fue actualizada con exito", HttpStatus.OK);
    }

    @Override
    public Collection<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }
}
