package com.cacsire.bibliotecanet.repository;

import com.cacsire.bibliotecanet.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
    BookEntity findByNameBook(String nameBook);

    BookEntity findByNameBookAndIdNot(String nameBook, Long idBook);


    //  Buscando por id de la categoria.Pongo Category ya q asi esta nombrado en mi entidad Book (category)
    List<BookEntity> findByCategory_Id(Long idCategory);

    List<BookEntity> findByAuthorContainingIgnoreCase(String author);

    List<BookEntity> findByNameBookContainingIgnoreCase(String nameBook);




}
