package com.unad.documentinspector;

import com.unad.documentinspector.dto.DocumentosDto;
import com.unad.documentinspector.dto.UsuariosDto;
import com.unad.documentinspector.entidad.Documento;
import com.unad.documentinspector.entidad.HistorialFirma;
import com.unad.documentinspector.entidad.Usuario;
import com.unad.documentinspector.repositorio.DocumentoRepository;
import com.unad.documentinspector.repositorio.HistorialFirmaRepository;
import com.unad.documentinspector.repositorio.UsuarioRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InicioController {


    @FXML
    public BorderPane menuInicial;
    @FXML
    private AnchorPane centroInicio;
    @FXML
    private TableView<DocumentosDto> tablaFuncion1;
    @FXML
    private TableColumn<DocumentosDto, String> nombre;
    @FXML
    private TableColumn<DocumentosDto, String> fecha;
    @FXML
    private TableColumn<DocumentosDto, String> tipo;
    @FXML
    private TableColumn<DocumentosDto, String> area;
    @FXML
    private TableColumn<DocumentosDto, String> registra;
    @FXML
    private TableColumn<DocumentosDto, String> asigna;
    @FXML
    private TableColumn<DocumentosDto, String> aprobacion;
    @FXML
    private Label userInicia;
    private final HistorialFirmaRepository historialFirmaRepository;


    private final DocumentoRepository documentoRepository;
    private final UsuarioRepository usuarioRepository;


    public InicioController() {
        this.documentoRepository = new DocumentoRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.historialFirmaRepository = new HistorialFirmaRepository();

    }

    @FXML
    public void initialize() {
        mostrarNombre();
        listarDocumentos();
    }


    public void refrescarLista() {
        listarDocumentos();
    }


    public void mostrarNombre() {
        userInicia.setText(SesionUsuario.getUsuario().getNombres());
    }


    @FXML
    public void listarDocumentos() {

        List<Documento> lst;
        if (SesionUsuario.getUsuario().getRol().getIdRol() == 1) {
            lst = documentoRepository.findAll();
        } else {
            lst = documentoRepository.findByUsuario(SesionUsuario.getUsuario());
        }


        List<DocumentosDto> resultados = lst.stream()
                .map(this::documentoDao)
                .collect(Collectors.toList());

        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        registra.setCellValueFactory(new PropertyValueFactory<>("userRegistra"));
        asigna.setCellValueFactory(new PropertyValueFactory<>("asignado"));
        aprobacion.setCellValueFactory(new PropertyValueFactory<>("aprobacio"));

        ObservableList<DocumentosDto> datos = FXCollections.observableArrayList(resultados);
        tablaFuncion1.setItems(datos);

    }

    public DocumentosDto documentoDao(Documento documento) {

        DocumentosDto documentoSalida = new DocumentosDto();
        documentoSalida.setNombre(documento.getNombre());
        documentoSalida.setFechaRegistro(documento.getFechaCargue().toString());
        documentoSalida.setTipo(documento.getTipoDocumento().getNombre());
        documentoSalida.setArea(documento.getUsuarioFk().getCargo().getArea().getNombre());
        documentoSalida.setUserRegistra(documento.getUsuarioFk().getNombres());
        documentoSalida.setAprobacio(documento.getEstadoDocumento().getNombre());
        documentoSalida.setCodigo(documento.getCodigoUnico());
        HistorialFirma histo = historialFirmaRepository.findByDocumento(documento);
        documentoSalida.setAsignado(histo != null ? histo.getFirma().getUsuario().getNombres() : "SIN ASIGNAR");
        return documentoSalida;


    }

    @FXML
    private void abrirAplicacion2() throws IOException {

        if (SesionUsuario.getUsuario().getRol().getIdRol() != 1) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("SIN ACCESO");
            alert.showAndWait();

        } else {
            FXMLLoader cargar = new FXMLLoader(getClass().getResource("funcionalidad2.fxml"));
            Parent vista = cargar.load();
            menuInicial.setCenter(vista);
        }
    }

    @FXML
    private void abrirAplicacion3() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("funcionalidad3.fxml"));
        Parent vista = cargar.load();
        menuInicial.setCenter(vista);
    }

    @FXML
    private void radicarDocumento() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("radicarDocumento.fxml"));
        Parent vista = cargar.load();
        menuInicial.setCenter(vista);
    }

    @FXML
    private void abrirDocumento() throws IOException {

        DocumentosDto abrirDocumento = tablaFuncion1.getSelectionModel().getSelectedItem();

        if (abrirDocumento == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("SELECCIONE UN DOCUMENTO A VISUALIZAR");
            alert.showAndWait();

        } else {

            FXMLLoader cargar = new FXMLLoader(getClass().getResource("abrirDocumento.fxml"));
            Parent vista = cargar.load();

            FuncionalidadesController controller = cargar.getController();
            controller.setDocumento(abrirDocumento);

            menuInicial.setCenter(vista);


        }
    }


    @FXML
    private void abrirAplicacion4() throws IOException {
        if (SesionUsuario.getUsuario().getRol().getIdRol() != 1) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("SIN ACCESO");
            alert.showAndWait();

        } else {
            FXMLLoader cargar = new FXMLLoader(getClass().getResource("funcionalidad4.fxml"));
            Parent vista = cargar.load();
            menuInicial.setCenter(vista);
        }
    }

    @FXML
    private void abrirAplicacion5() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("funcionalidad5.fxml"));
        Parent vista = cargar.load();
        menuInicial.setCenter(vista);

    }

    @FXML
    private void abrirInfo1() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info1.fxml"));
        Parent vista = cargar.load();
        menuInicial.setCenter(vista);
    }

    @FXML
    private void abrirInfo2() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info2.fxml"));
        Parent vista = cargar.load();
        menuInicial.setCenter(vista);
    }

    @FXML
    private void abrirInfo3() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info3.fxml"));
        Parent vista = cargar.load();
        menuInicial.setCenter(vista);
    }

    @FXML
    private void abrirInfo4() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info4.fxml"));
        Parent vista = cargar.load();
        menuInicial.setCenter(vista);
    }

    @FXML
    public void cerrarVentana() {
        refrescarLista();
        menuInicial.setCenter(centroInicio);

    }

    @FXML
    private void cerrarPrograma() {
        Platform.exit();
    }


    public void cerrarPerfil(ActionEvent event) throws IOException {
        FXMLLoader lanzarMenu = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent vista = lanzarMenu.load();
        Scene sceneActual = ((Node) event.getSource()).getScene();
        sceneActual.setRoot(vista);
    }


}
