package com.unad.documentinspector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoDto {

    private String nombre;
    private String fechaCrea;
    private String userCrea;
    private String cargos;

    public CargoDto() {
    }


}
