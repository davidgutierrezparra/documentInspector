package com.unad.documentinspector.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "AREA")
@Getter
@Setter
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_seq_gen")
    @SequenceGenerator(name = "area_seq_gen", sequenceName = "SEQ_AREA", allocationSize = 1)
    @Column(name = "ID_AREA")
    private Long idArea;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CREA")
    private LocalDate fechaCrea;
    @Column(name = "USER_CREA")
    private Long userCrea;

}
