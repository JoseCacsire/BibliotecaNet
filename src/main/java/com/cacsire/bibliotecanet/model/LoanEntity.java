package com.cacsire.bibliotecanet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Prestamos")
@Entity
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prestamo_id")
    private Long id;

    private LocalDate prestamoDate;

    private LocalDate returnDate;

    private String statusPrestamo;

    private boolean deletePrestamo;


}
