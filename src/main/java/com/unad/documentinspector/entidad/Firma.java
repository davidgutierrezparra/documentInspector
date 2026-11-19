package com.unad.documentinspector.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "FIRMA")
@Getter
@Setter
public class Firma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "firma_seq_gen")
    @SequenceGenerator(name = "firma_seq_gen", sequenceName = "SEQ_FIRMA", allocationSize = 1)
    @Column(name = "ID_FIRMA")
    private Long idFirma;
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "ESTADO")
    private Long estado;
    @Column(name = "FECHA_CREA")
    private LocalDate fechaCrea;
    @Column(name = "USER_CREA")
    private Long userCrea;
    @ManyToOne
    @JoinColumn(name = "USUARIO_FK")
    private Usuario usuario;

    public Firma() {
    }

}
