package com.unad.documentinspector.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq_gen")
    @SequenceGenerator(name = "usuario_seq_gen", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "IDENTIFICACION")
    private Long identificacion;
    @Column(name = "CANTIDAD_CARGADOS")
    private Long cantidadCargados;
    @Column(name = "ESTADO_FIRMA")
    private String estadoFirma;
    @Column(name = "FECHA_CREA")
    private LocalDate fechaCrea;
    @Column(name = "USER_CREA")
    private Long userCrea;
    @ManyToOne
    @JoinColumn(name = "ROL_FK")
    private Rol rol;
    @ManyToOne
    @JoinColumn(name = "CARGO_FK")
    private Cargo cargo;
    @Column(name = "PASSWORD")
    private String password;


    public Usuario() {
    }

    public Usuario(Long idUsuario) {
    this.idUsuario = idUsuario;
    }
}
