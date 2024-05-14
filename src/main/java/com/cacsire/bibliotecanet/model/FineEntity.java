package com.cacsire.bibliotecanet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Multas")
@Entity
public class FineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multa_id")
    private Long id;

    private String descriptionMulta;

    private String typeMulta;

    private LocalDate multaDate;

    private String statusMulta;

    private double montoTotal;

    private boolean deleteMulta;

//  Muchas multas puede haber en un prestamo
    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    private LoanEntity loan;
}
