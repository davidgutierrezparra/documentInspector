package com.unad.documentinspector.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORIAL_FIRMA")
@Getter
@Setter
public class HistorialFirma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial_firma_seq_gen")
    @SequenceGenerator(name = "historial_firma_seq_gen", sequenceName = "SEQ_HISTORIAL_FIRMA", allocationSize = 1)
    @Column(name = "ID_HISTORIAL")
    private Long idHistorial;
    @Column(name = "CODIGO_FIRMA")
    private Long codigoFirma;
    @ManyToOne
    @JoinColumn(name = "FIRMA_FK")
    private Firma firma;
    @ManyToOne
    @JoinColumn(name = "DOCUMENTO_FK")
    private Documento documento;
    @Column(name = "FECHA_CREA")
    private LocalDateTime fechaCrea;


}
