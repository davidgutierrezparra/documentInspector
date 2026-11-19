package com.unad.documentinspector.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ESTADO_DOCUMENTO")
@Getter
@Setter
public class EstadoDocumento {

    @Id
    @Column(name = "ID_ESTADO_DOCUMENTO")
    private Long idEstadoDocumento;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ORDEN")
    private Long orden;

    public EstadoDocumento() {
    }

    public EstadoDocumento(Long idEstadoDocumento) {
    this.idEstadoDocumento = idEstadoDocumento;
    }

}
