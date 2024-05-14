package com.cacsire.bibliotecanet.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Detalle_Prestamo")
@Entity
public class LoanDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


// Esta relación es útil si tu sistema permite prestar múltiples libros a la vez por cada préstamo.

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private BookEntity libro;

    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    private LoanEntity prestamo;


}
