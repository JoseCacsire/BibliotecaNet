package com.cacsire.bibliotecanet.service;

import com.cacsire.bibliotecanet.model.BookEntity;
import com.cacsire.bibliotecanet.repository.BookRepository;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

public interface BookService {
    List<BookEntity> findAll();


    public Collection<BookEntity> findByCategory(Long idCategory);

    ResponseEntity<String> saveBook(BookEntity book);

    ResponseEntity<?> deleteBook(Long bookId);

    ResponseEntity<?> updateBook(Long bookId, BookEntity bookUpdate);

    ResponseEntity<?> findBookById(Long bookId);

    Collection<BookEntity> searchBooksByAuthorOrName(String searchTerm);
}
