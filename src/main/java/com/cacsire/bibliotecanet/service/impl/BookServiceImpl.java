package com.cacsire.bibliotecanet.service.impl;

import com.cacsire.bibliotecanet.Utils.UtilsLibrary;
import com.cacsire.bibliotecanet.constants.LibraryConstants;
import com.cacsire.bibliotecanet.model.BookEntity;
import com.cacsire.bibliotecanet.model.CategoryEntity;
import com.cacsire.bibliotecanet.repository.BookRepository;
import com.cacsire.bibliotecanet.repository.CategoryRepository;
import com.cacsire.bibliotecanet.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Collection<BookEntity> findByCategory(Long idCategory) {
        return  (Collection<BookEntity>) bookRepository.findByCategory_Id(idCategory);
    }

    @Override
    public ResponseEntity<String> saveBook(BookEntity book) {
        if (!validateBookData(book)) {
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        BookEntity existingBook = bookRepository.findByNameBook(book.getNameBook());

        CategoryEntity categoryEntity = categoryRepository.findById(book.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("No existe esta categoria"));

        if (existingBook != null) {
            return UtilsLibrary.getResponseEntity("Este libro ya existe", HttpStatus.BAD_REQUEST);
        }
        book.setStatusBook("Disponible");
        bookRepository.save(book);
        return UtilsLibrary.getResponseEntity("El libro fue registrado con éxito", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteBook(Long bookId) {
        Optional<BookEntity> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return UtilsLibrary.getResponseEntity("No existe el libro con id: " + bookId, HttpStatus.BAD_REQUEST);
        } else {
            bookRepository.deleteById(bookId);
            return UtilsLibrary.getResponseEntity("El libro fue eliminado con éxito", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookEntity bookUpdate) {
        Optional<BookEntity> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return UtilsLibrary.getResponseEntity("No existe el libro con id: " + bookId, HttpStatus.BAD_REQUEST);
        }

        if (!validateBookData(bookUpdate)) {
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        BookEntity existingBook = bookRepository.findByNameBookAndIdNot(bookUpdate.getNameBook(), bookId);
        if (existingBook != null) {
            return UtilsLibrary.getResponseEntity("Este libro ya existe", HttpStatus.BAD_REQUEST);
        }

        book.get().setNameBook(bookUpdate.getNameBook());
        book.get().setAuthor(bookUpdate.getAuthor());
        book.get().setDescription(bookUpdate.getDescription());
        book.get().setPublicationDate(bookUpdate.getPublicationDate());
        book.get().setStatusBook(bookUpdate.getStatusBook());
        book.get().setPicture(bookUpdate.getPicture());
        book.get().setCategory(bookUpdate.getCategory());

        bookRepository.save(book.get());
        return UtilsLibrary.getResponseEntity("El libro fue actualizado con éxito", HttpStatus.OK);
    }

    public ResponseEntity<?> findBookById(Long bookId) {
        Optional<BookEntity> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return UtilsLibrary.getResponseEntity("No existe el libro con ID: " + bookId, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Collection<BookEntity> searchBooksByAuthorOrName(String searchTerm) {
        List<BookEntity> booksByAuthor = bookRepository.findByAuthorContainingIgnoreCase(searchTerm);
        List<BookEntity> booksByName = bookRepository.findByNameBookContainingIgnoreCase(searchTerm);
        booksByAuthor.addAll(booksByName);
        return booksByAuthor;
    }

    public static boolean validateBookData(BookEntity book) {
        return book.getNameBook() != null && !book.getNameBook().isEmpty() &&
                book.getAuthor() != null && !book.getAuthor().isEmpty() &&
                book.getDescription() != null && !book.getDescription().isEmpty() &&
                book.getPublicationDate() != null &&
                book.getStatusBook() != null && !book.getStatusBook().isEmpty() &&
                book.getPicture() != null && !book.getPicture().isEmpty() &&
                book.getCategory() != null;
    }

}
