package com.unad.documentinspector.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "CARGO")
@Getter
@Setter
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_seq_gen")
    @SequenceGenerator(name = "cargo_seq_gen", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "ID_CARGO")
    private Long idCargo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CREA")
    private LocalDate fechaCrea;
    @Column(name = "USER_CREA")
    private Long userCrea;
    @Column(name = "ESTADO")
    private Long estado;
    @ManyToOne
    @JoinColumn(name = "AREA_FK")
    private Area area;

}
