package com.unad.documentinspector;

import com.unad.documentinspector.entidad.Usuario;

public class SesionUsuario {

    private static Usuario usuario;

    public static void setUsuario(Usuario id) {
        usuario = id;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

}
