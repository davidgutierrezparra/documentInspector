package com.unad.documentinspector.dto;

public class DocumentosDto {

    private String nombre;
    private String fechaRegistro;
    private String tipo;
    private String area;
    private String userRegistra;
    private String aprobacio;
    private String codigo;
    private Long temp;
    private String asignado;

    public DocumentosDto(String nombre, String fechaRegistro, String tipo, String area, String userRegistra, String aprobacion, String codigo, Long temp) {
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.tipo = tipo;
        this.area = area;
        this.userRegistra = userRegistra;
        this.aprobacio = aprobacion;
        this.codigo = codigo;
        this.temp = temp;
    }

    public DocumentosDto() {

    }

    public String getNombre() {return nombre;}
    public String getFechaRegistro() {return fechaRegistro;}
    public String getTipo() {return tipo;}
    public String getArea() {return area;}
    public String getUserRegistra() {return userRegistra;}
    public String getAprobacio() {return aprobacio;}
    public String getCodigo() {return codigo;}
    public Long getTemp() {return temp;}
    public String getAsignado() {return asignado;}

    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setFechaRegistro(String fechaRegistro) {this.fechaRegistro = fechaRegistro;}
    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setArea(String area) {this.area = area;}
    public void setUserRegistra(String userRegistra) {this.userRegistra = userRegistra;}
    public void setAprobacio(String aprobacion) {this.aprobacio = aprobacion;}
    public void setCodigo(String codigo) {this.codigo = codigo;}
    public void setTemp(Long temp) {this.temp = temp;}
    public void setAsignado(String asignado) {this.asignado = asignado;}

}
