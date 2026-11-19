package com.unad.documentinspector.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "DOCUMENTO")
@Getter
@Setter
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento_seq_gen")
    @SequenceGenerator(name = "documento_seq_gen", sequenceName = "SEQ_DOCUMENTO", allocationSize = 1)
    @Column(name = "ID_DOCUMENTO")
    private Long idDocumento;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CARGUE")
    private LocalDate fechaCargue;
    @Column(name = "CODIGO_UNICO")
    private String codigoUnico;
    @Column(name = "CANTIDAD_FIRMAS")
    private Long cantidadFirmas;
    @Column(name = "HASH_FINAL")
    private String hashFinal;
    @ManyToOne
    @JoinColumn(name = "USUARIO_FK")
    private Usuario usuarioFk;
    @ManyToOne
    @JoinColumn(name = "TIPO_DOCUMENTO_FK")
    private TipoDocumento tipoDocumento;
    @ManyToOne
    @JoinColumn(name = "ESTADO_DOCUMENTO_FK")
    private EstadoDocumento estadoDocumento;
    @Column(name = "TEMP")
    private Long temp;

    public Documento() {
    }
}
