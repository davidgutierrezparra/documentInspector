package com.unad.documentinspector.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TIPO_DOCUMENTO")
@Getter
@Setter
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_documento_seq_gen")
    @SequenceGenerator(name = "tipo_documento_seq_gen", sequenceName = "SEQ_TIPO_DOCUMENTO", allocationSize = 1)
    @Column(name = "ID_TIPO_DOCUMENTO")
    private Long idTipoDocumento;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CREA")
    private LocalDate fechaCrea;
    @Column(name = "USER_CREA")
    private Long userCrea;
    @Column(name = "CODIGO_TRD")
    private String codigoTrd;

    public TipoDocumento() {
    }

    public TipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

}
