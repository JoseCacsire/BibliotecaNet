package com.cacsire.bibliotecanet.controller;

import com.cacsire.bibliotecanet.Utils.UtilsLibrary;
import com.cacsire.bibliotecanet.constants.LibraryConstants;
import com.cacsire.bibliotecanet.model.BookEntity;
import com.cacsire.bibliotecanet.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllBooks() {
        try {
            List<BookEntity> books = bookService.findAll();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener la lista de libros.", e);
            e.printStackTrace();
//            En observacion
//            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/user")
    public ResponseEntity<?> getAllBooksUser(){
        try {
            Collection<BookEntity> itemsBooks =  bookService.findAll();
            return new ResponseEntity<>(itemsBooks, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> createBook(@RequestBody(required = true) BookEntity book) {
        try {
            return bookService.saveBook(book);
        } catch (Exception e) {
            log.error("catchhh");
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/admin/update/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookEntity bookUpdate){
        try{
            return bookService.updateBook(bookId, bookUpdate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/admin/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId){
        try{
            return bookService.deleteBook(bookId);
        }catch (Exception e){
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/category/{categoryId}")
    public Collection<BookEntity> findByCategory(@PathVariable Long categoryId) {
        return bookService.findByCategory(categoryId);
    }


    @GetMapping("user/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        try {
            return bookService.findBookById(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooksByAuthorOrName(@RequestParam String searchTerm) {
        try {
            Collection<BookEntity> searchResult = bookService.searchBooksByAuthorOrName(searchTerm);
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
