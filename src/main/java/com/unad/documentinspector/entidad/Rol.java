package com.unad.documentinspector.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ROL")
@Getter
@Setter
public class Rol {

    @Id
    @Column(name = "ID_ROL")
    private Long idRol;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_CREA")
    private LocalDate fechaCrea;
    @Column(name = "USER_CREA")
    private Long userCrea;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private Long estado;

    public Rol() {
    }

    public Rol(Long idRol) {
        this.idRol = idRol;
    }

}
