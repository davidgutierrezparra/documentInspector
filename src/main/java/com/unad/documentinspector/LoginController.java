package com.unad.documentinspector;

import com.unad.documentinspector.entidad.Usuario;
import com.unad.documentinspector.repositorio.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField userIngreso;
    @FXML
    private PasswordField contrasenaIngreso;
    @FXML
    public BorderPane login;
    private final UsuarioRepository usuarioRepository;


    public LoginController() {
        this.usuarioRepository = new UsuarioRepository();
    }


    @FXML
    private void cerrarPrograma() {
        Platform.exit();
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {

        if (userIngreso.getText().isEmpty() || contrasenaIngreso.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("COMPLETE LOS CAMPOS PARA INGRESAR");
            alert.showAndWait();
        } else {
            long user = Long.parseLong(userIngreso.getText());
            String contrasena = contrasenaIngreso.getText();
            Usuario userLog = usuarioRepository.findByIdentificacion(user);


            if (userLog != null && contrasena.equals(userLog.getPassword())) {
                SesionUsuario.setUsuario(userLog);
                FXMLLoader lanzarMenu = new FXMLLoader(getClass().getResource("menuInicial.fxml"));
                Parent vista = lanzarMenu.load();
                Scene sceneActual = ((Node) event.getSource()).getScene();
                sceneActual.setRoot(vista);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("DOCUMENT INSPECTOR");
                alert.setHeaderText(null);
                alert.setContentText("DATOS DE USUARIO INCORRECTOS");
                alert.showAndWait();
            }
        }
    }

}
