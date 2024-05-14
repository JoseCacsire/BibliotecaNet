package com.cacsire.bibliotecanet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Libros")
@Entity
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "libro_id")
    private Long id;

    private String nameBook;
    private String author;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate publicationDate;
    private String statusBook;
    private String picture;


    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoryEntity category;


}
