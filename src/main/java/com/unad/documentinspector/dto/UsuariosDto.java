package com.unad.documentinspector.dto;

public class UsuariosDto {


    private String nombres;
    private Long identificacion;
    private String cargo;
    private String area;
    private Long docuemntosCargados;
    private String firma;

    public UsuariosDto(String nombres, Long identificacion, String cargo, String area, Long docuemntosCargados, String firma) {
        this.nombres = nombres;
        this.identificacion = identificacion;
        this.cargo = cargo;
        this.area = area;
        this.docuemntosCargados = docuemntosCargados;
        this.firma = firma;
    }

    public UsuariosDto() {

    }

    public String getNombres() {return nombres;}
    public Long getIdentificacion() {return identificacion;}
    public String getCargo() {return cargo;}
    public String getArea() {return area;}
    public Long getDocuemntosCargados() {return docuemntosCargados;}
    public String getFirma() {return firma;}

    public void setNombres(String nombres) {this.nombres = nombres;}
    public void setIdentificacion(Long identificacion) {this.identificacion = identificacion;}
    public void setCargo(String cargo) {this.cargo = cargo;}
    public void setArea(String area) {this.area = area;}
    public void setDocuemntosCargados(Long docuemntosCargados) {this.docuemntosCargados = docuemntosCargados;}
    public void setFirma(String aprobacion) {this.firma = aprobacion;}


}
