package com.unad.documentinspector;

import com.unad.documentinspector.dto.AreaDto;
import com.unad.documentinspector.dto.CargoDto;
import com.unad.documentinspector.dto.DocumentosDto;
import com.unad.documentinspector.dto.UsuariosDto;
import com.unad.documentinspector.entidad.*;
import com.unad.documentinspector.repositorio.*;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class FuncionalidadesController {

    @FXML
    public AnchorPane funcionalidad2;
    @FXML
    public AnchorPane funcionalidad3;
    @FXML
    public AnchorPane funcionalidad4;
    @FXML
    public AnchorPane funcionalidad5;
    @FXML
    public AnchorPane radicarDocumento;
    @FXML
    public AnchorPane abrirDocumento;
    @FXML
    public TableView<UsuariosDto> tablaFuncion2;

    @FXML
    public TableColumn<UsuariosDto, String> nombres;
    @FXML
    public TableColumn<UsuariosDto, String> identificacion;
    @FXML
    public TableColumn<UsuariosDto, String> cargo;
    @FXML
    public TableColumn<UsuariosDto, String> area;
    @FXML
    public TableColumn<UsuariosDto, String> docuemntosCargados;
    @FXML
    public TableColumn<UsuariosDto, String> firma;

    @FXML
    public TableView<DocumentosDto> tablaFuncion3;

    @FXML
    private TableColumn<DocumentosDto, String> nombre;
    @FXML
    private TableColumn<DocumentosDto, String> fecha;
    @FXML
    private TableColumn<DocumentosDto, String> tipo;
    @FXML
    private TableColumn<DocumentosDto, String> areas;
    @FXML
    private TableColumn<DocumentosDto, String> registra;
    @FXML
    private TableColumn<DocumentosDto, String> aprobacion;

    @FXML
    public TableView<AreaDto> tablaFuncion4;

    @FXML
    private TableColumn<DocumentosDto, String> nombreArea;
    @FXML
    private TableColumn<DocumentosDto, String> fechaCrea;
    @FXML
    private TableColumn<DocumentosDto, String> userCrea;
    @FXML
    private TableColumn<DocumentosDto, String> cantidadCargos;

    @FXML
    public TableView<CargoDto> tablaFuncion4c;

    @FXML
    private TableColumn<DocumentosDto, String> nombreCargo;
    @FXML
    private TableColumn<DocumentosDto, String> fechaCreaCargo;
    @FXML
    private TableColumn<DocumentosDto, String> userCreaCargo;
    @FXML
    private TableColumn<DocumentosDto, String> areaCargo;


    private final UsuarioRepository usuarioRepository;
    private final DocumentoRepository documentoRepository;
    private final FirmaRepository firmaRepository;
    private final HistorialFirmaRepository historialFirmaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final RolRepository rolRepository;
    private final CargoRepository cargoRepository;
    private final AreaRepository areaRepository;

    @FXML
    private TextField nombreDocumento;
    @FXML
    private Label lblArchivo;
    private File archivoPdf;
    @FXML
    private VBox contenedorPaginas;
    @FXML
    private ComboBox<String> tipoDocumento;
    @FXML
    private ComboBox<String> asignar;

    private DocumentosDto documento;

    @FXML
    private ComboBox<String> rol;
    @FXML
    private ComboBox<String> cargos;

    @FXML
    private ComboBox<String> areasAsignar;

    @FXML
    private TextField nom;
    @FXML
    private TextField cedula;
    @FXML
    private Button guardar;
    @FXML
    private ImageView imgDocumento;
    @FXML
    private ImageView imgDocumentoo;
    @FXML
    private ImageView imgDocumentooo;


    public FuncionalidadesController() {

        this.documentoRepository = new DocumentoRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.tipoDocumentoRepository = new TipoDocumentoRepository();
        this.firmaRepository = new FirmaRepository();
        this.historialFirmaRepository = new HistorialFirmaRepository();
        this.rolRepository = new RolRepository();
        this.cargoRepository = new CargoRepository();
        this.areaRepository = new AreaRepository();
    }


    @FXML
    public void initialize() throws IOException {


        if (funcionalidad2 != null) {
            listarUsuarios();

            List<Rol> lstRoles = rolRepository.findAll();
            List<String> datosRol = lstRoles.stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());
            rol.getItems().setAll(datosRol);

            List<Cargo> lstCargos = cargoRepository.findAll();
            List<String> datosCargo = lstCargos.stream()
                    .map(Cargo::getNombre)
                    .collect(Collectors.toList());
            cargos.getItems().setAll(datosCargo);

        }

        if (radicarDocumento != null) {
            List<TipoDocumento> lstTipoDocumento = tipoDocumentoRepository.findAll();
            List<String> datos = lstTipoDocumento.stream()
                    .map(TipoDocumento::getNombre)
                    .collect(Collectors.toList());
            tipoDocumento.getItems().setAll(datos);


            List<Usuario> lstUsuarios = usuarioRepository.findAll();
            List<String> datosUsuarios = lstUsuarios.stream()
                    .map(Usuario::getNombres)
                    .toList();
            asignar.getItems().setAll(datosUsuarios);

        }

        if (funcionalidad3 != null) {
            listarDocumentos();
        }

        if (funcionalidad4 != null) {

            listarAreas();
            listarCargos();


            List<Area> lstAreas = areaRepository.findAll();
            List<String> datosArea = lstAreas.stream()
                    .map(Area::getNombre)
                    .collect(Collectors.toList());
            areasAsignar.getItems().setAll(datosArea);


        }

        if (funcionalidad5 != null) {


            cargarImagen();

        }


    }


    @FXML
    private void cargarImagen() {
        Image imagen = new Image(getClass().getResource("/com/unad/imagenes/Blog1.png").toExternalForm());
        imgDocumento.setImage(imagen);

        Image imagenn = new Image(getClass().getResource("/com/unad/imagenes/Blog2.png").toExternalForm());
        imgDocumentoo.setImage(imagenn);

        Image imagennn = new Image(getClass().getResource("/com/unad/imagenes/Blog3.png").toExternalForm());
        imgDocumentooo.setImage(imagennn);
    }


    @FXML
    private void abrirPagina1() {
        try {
            Desktop.getDesktop().browse(
                    new URI("https://www.funcionpublica.gov.co/eva/gestornormativo/norma.php?i=4275")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirPagina2() {
        try {
            Desktop.getDesktop().browse(
                    new URI("https://www.funcionpublica.gov.co/eva/gestornormativo/norma.php?i=175127")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirPagina3() {
        try {
            Desktop.getDesktop().browse(
                    new URI("https://www.funcionpublica.gov.co/eva/gestornormativo/norma.php?i=50583")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void refrescarListaFuncion2() {
        listarUsuarios();

        List<Rol> lstRoles = rolRepository.findAll();
        List<String> datosRol = lstRoles.stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        rol.getItems().setAll(datosRol);

        List<Cargo> lstCargos = cargoRepository.findAll();
        List<String> datosCargo = lstCargos.stream()
                .map(Cargo::getNombre)
                .collect(Collectors.toList());
        cargos.getItems().setAll(datosCargo);
    }

    public void refrescarListaFuncion3() {


        // tablaFuncion3.getItems().clear();

        listarDocumentos();


    }

    public void setDocumento(DocumentosDto documento) {
        this.documento = documento;
        consultarDocumento(documento.getCodigo());
    }

    @FXML
    private void mostrarComponentes() {
        nom.setOpacity(1);
        cedula.setOpacity(1);
        rol.setOpacity(1);
        cargos.setOpacity(1);
        guardar.setOpacity(1);
    }


    private void ocultarComponentes() {
        nom.clear();
        cedula.clear();

        nom.setOpacity(0);
        cedula.setOpacity(0);
        rol.setOpacity(0);
        cargos.setOpacity(0);
        guardar.setOpacity(0);
    }

    @FXML
    private void guardarUsuario() throws IOException, NoSuchAlgorithmException {

        if (nom.getText().isEmpty() || cedula.getText().isEmpty() || rol.getValue() == null || cargos.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("COMPLETE LOS CAMPOS PARA GUARDAR");
            alert.showAndWait();
        } else {

            Usuario user = new Usuario();
            user.setNombres(nom.getText());
            user.setIdentificacion(Long.valueOf(cedula.getText()));
            user.setCantidadCargados(0L);
            user.setEstadoFirma("ALMACENADA");
            user.setFechaCrea(LocalDate.now());
            user.setUserCrea(SesionUsuario.getUsuario().getIdentificacion());
            user.setRol(rolRepository.findByNombre(rol.getValue()));
            user.setCargo(cargoRepository.findByNombre(cargos.getValue()));
            user.setPassword("12345");
            usuarioRepository.save(user);

            Firma firma = new Firma();
            firma.setCodigo(generarUUID());
            firma.setEstado(1L);
            firma.setFechaCrea(LocalDate.now());
            firma.setUserCrea(SesionUsuario.getUsuario().getIdentificacion());
            firma.setUsuario(user);
            firmaRepository.save(firma);

            ocultarComponentes();
            refrescarListaFuncion2();


        }
    }

    @FXML
    public void listarUsuarios() {

        List<Usuario> lst = usuarioRepository.findAll();

        List<UsuariosDto> resultados = lst.stream()
                .map(this::usuarioDao)
                .collect(Collectors.toList());

        nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        identificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        cargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        docuemntosCargados.setCellValueFactory(new PropertyValueFactory<>("docuemntosCargados"));
        firma.setCellValueFactory(new PropertyValueFactory<>("firma"));

        ObservableList<UsuariosDto> datos = FXCollections.observableArrayList(resultados);
        tablaFuncion2.setItems(datos);

    }

    @FXML
    public void listarDocumentos() {

        Firma firma = firmaRepository.findByUsuario(SesionUsuario.getUsuario());

        if (firma == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("NO CUENTA CON PERMISOS PARA APROBAR DOCUMENTOS.");
            alert.showAndWait();

        } else {

            List<HistorialFirma> historialFirma = historialFirmaRepository.findBySinFirma(firma);

            if (historialFirma.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("DOCUMENT INSPECTOR");
                alert.setHeaderText(null);
                alert.setContentText("NO TIENE DOCUMENTOS PENDIENTES PARA FIRMAR.");
                alert.showAndWait();

            } else {

                List<Documento> lst = new ArrayList<>();
                historialFirma.forEach(obj -> {
                    Documento editado = obj.getDocumento();
                    editado.setTemp(obj.getIdHistorial());
                    lst.add(editado);
                });

                List<DocumentosDto> resultados = lst.stream()
                        .map(this::documentoDao)
                        .collect(Collectors.toList());

                nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                fecha.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));
                tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                areas.setCellValueFactory(new PropertyValueFactory<>("area"));
                registra.setCellValueFactory(new PropertyValueFactory<>("userRegistra"));
                aprobacion.setCellValueFactory(new PropertyValueFactory<>("aprobacio"));

                ObservableList<DocumentosDto> datos = FXCollections.observableArrayList(resultados);
                tablaFuncion3.setItems(datos);

            }
        }
    }


    @FXML
    public void listarCargos() {


        List<Cargo> lstCargos = cargoRepository.findAll();
        List<CargoDto> datosCargo = lstCargos.stream()
                .map(this::cargoDao)
                .collect(Collectors.toList());

        nombreCargo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fechaCreaCargo.setCellValueFactory(new PropertyValueFactory<>("fechaCrea"));
        userCreaCargo.setCellValueFactory(new PropertyValueFactory<>("userCrea"));
        areaCargo.setCellValueFactory(new PropertyValueFactory<>("cargos"));

        ObservableList<CargoDto> datos = FXCollections.observableArrayList(datosCargo);
        tablaFuncion4c.setItems(datos);

    }


    @FXML
    public void listarAreas() {


        List<Area> lstAreas = areaRepository.findAll();
        List<AreaDto> datosArea = lstAreas.stream()
                .map(this::areaDao)
                .collect(Collectors.toList());

        nombreArea.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fechaCrea.setCellValueFactory(new PropertyValueFactory<>("fechaCrea"));
        userCrea.setCellValueFactory(new PropertyValueFactory<>("userCrea"));
        cantidadCargos.setCellValueFactory(new PropertyValueFactory<>("cargos"));

        ObservableList<AreaDto> datos = FXCollections.observableArrayList(datosArea);
        tablaFuncion4.setItems(datos);

    }


    public UsuariosDto usuarioDao(Usuario usuario) {

        UsuariosDto usuarioSalida = new UsuariosDto();
        usuarioSalida.setNombres(usuario.getNombres());
        usuarioSalida.setIdentificacion(usuario.getIdentificacion());
        usuarioSalida.setCargo(usuario.getCargo().getNombre());
        usuarioSalida.setArea(usuario.getCargo().getArea().getNombre());
        usuarioSalida.setDocuemntosCargados(usuario.getCantidadCargados());
        usuarioSalida.setFirma(usuario.getEstadoFirma());
        return usuarioSalida;

    }

    public AreaDto areaDao(Area area) {

        AreaDto areaSalida = new AreaDto();
        areaSalida.setNombre(area.getNombre());
        areaSalida.setFechaCrea(area.getFechaCrea().toString());
        areaSalida.setUserCrea(area.getUserCrea().toString());
        List<Cargo> cargos = cargoRepository.findByLstArea(area);
        areaSalida.setCargos(cargos.isEmpty() ? String.valueOf(0) : String.valueOf(cargos.size()));
        return areaSalida;

    }


    public CargoDto cargoDao(Cargo cargo) {

        CargoDto cargoSalida = new CargoDto();
        cargoSalida.setNombre(cargo.getNombre());
        cargoSalida.setFechaCrea(cargo.getFechaCrea().toString());
        cargoSalida.setUserCrea(cargo.getUserCrea().toString());
        cargoSalida.setCargos(cargo.getArea().getNombre());
        return cargoSalida;

    }


    @FXML
    private void guardarDocumento(ActionEvent evento) throws IOException, NoSuchAlgorithmException {

        if (nombreDocumento.getText().isEmpty() || tipoDocumento.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("COMPLETE LOS CAMPOS PARA GUARDAR");
            alert.showAndWait();
        } else {
            String codigoDocumento = generarUUID();
            String nombre = nombreDocumento.getText();
            guardarPdfEnCarpeta(codigoDocumento + ".pdf");

            Documento documento = new Documento();
            documento.setNombre(nombre);
            documento.setFechaCargue(LocalDate.now());
            documento.setCodigoUnico(codigoDocumento + ".pdf");
            documento.setCantidadFirmas(asignar.getValue() == null ? 0 : 1L);
            documento.setHashFinal(generarHashSHA256(archivoPdf));
            documento.setUsuarioFk(SesionUsuario.getUsuario());
            documento.setTipoDocumento(tipoDocumentoRepository.findByNombre(tipoDocumento.getValue()));
            documento.setEstadoDocumento(asignar.getValue() == null ? new EstadoDocumento(2L) : new EstadoDocumento(1L));
            documentoRepository.save(documento);


            if (asignar.getValue() != null) {
                asignarDocumento(asignar.getValue(), documento);
            }

            Usuario userCrea = SesionUsuario.getUsuario();
            userCrea.setCantidadCargados(userCrea.getCantidadCargados() + 1);
            usuarioRepository.update(userCrea);

            mostrarAlerta();
            cerrarVista(evento);
        }
    }


    @FXML
    private void aprobarDocumento(ActionEvent evento) throws IOException, NoSuchAlgorithmException {


        DocumentosDto aprobarDocumento = tablaFuncion3.getSelectionModel().getSelectedItem();

        if (aprobarDocumento == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("SELECCIONE UN DOCUMENTO PARA SU APROBACION.");
            alert.showAndWait();

        } else {

            FXMLLoader cargar = new FXMLLoader(getClass().getResource("aprobarDocumento.fxml"));
            Parent vista = cargar.load();
            AprobarDocumentos controller = cargar.getController();
            controller.setDocumento(aprobarDocumento);
            Stage nuevaVentana = new Stage();
            nuevaVentana.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(vista);
            scene.setFill(Color.TRANSPARENT);
            nuevaVentana.setScene(scene);
            nuevaVentana.setWidth(1150);
            nuevaVentana.setHeight(715);
            nuevaVentana.setResizable(false);
            nuevaVentana.show();

        }

    }

    public void asignarDocumento(String user, Documento documento) {

        HistorialFirma historial = new HistorialFirma();
        Usuario usuario = usuarioRepository.findByNombre(user);
        historial.setFirma(firmaRepository.findByUsuario(usuario));
        historial.setDocumento(documento);
        historialFirmaRepository.save(historial);


    }

    @FXML
    private void seleccionarPdf() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar documento PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File archivo = fileChooser.showOpenDialog(lblArchivo.getScene().getWindow());
        if (archivo != null) {
            archivoPdf = archivo;
            lblArchivo.setText(archivo.getName());
        }
        visualizarPdf(archivoPdf);
    }

    private void guardarPdfEnCarpeta(String nuevoNombre) {

        if (archivoPdf == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("DOCUMENTO OBLIGATORIO");
            alert.showAndWait();
            return;
        }
        Path carpetaDestino = Paths.get("C:\\GestionDocumental\\Documentos");

        try {
            // Crear la carpeta si todavía no existe
            Files.createDirectories(carpetaDestino);
            // Construir la ruta de destino
            Path destino = carpetaDestino.resolve(nuevoNombre);
            // Copiar el archivo
            Files.copy(archivoPdf.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el PDF.");
        }
    }

    private void visualizarPdf(File archivo) throws IOException {

        contenedorPaginas.getChildren().clear();

        try (PDDocument documento = Loader.loadPDF(archivo)) {

            PDFRenderer renderer = new PDFRenderer(documento);

            for (int i = 0; i < documento.getNumberOfPages(); i++) {

                BufferedImage imagen =
                        renderer.renderImageWithDPI(i, 100);

                Image imagenJavaFX = SwingFXUtils.toFXImage(imagen, null);

                ImageView pagina = new ImageView(imagenJavaFX);
                pagina.setPreserveRatio(true);
                pagina.setFitWidth(750);

                contenedorPaginas.getChildren().add(pagina);
            }
        }
    }

    @FXML
    private void cerrarVista(ActionEvent event) throws IOException {

        FXMLLoader lanzarMenu = new FXMLLoader(getClass().getResource("menuInicial.fxml"));
        Parent vista = lanzarMenu.load();
        Scene sceneActual = ((Node) event.getSource()).getScene();
        sceneActual.setRoot(vista);

    }

    private void mostrarAlerta() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("DOCUMENT INSPECTOR");
        alert.setHeaderText(null);
        alert.setContentText("TRAMITE RADICADO EXITOSAMENTE");

        alert.show();

        PauseTransition pausa = new PauseTransition(
                Duration.seconds(3)
        );

        pausa.setOnFinished(event -> alert.close());
        pausa.play();
    }

    private void consultarDocumento(String nombreArchivo) {

        Path rutaDocumento = Paths.get(
                "C:\\GestionDocumental\\Documentos",
                nombreArchivo
        );

        // Verificar que el archivo exista
        if (!Files.exists(rutaDocumento)
                || !Files.isRegularFile(rutaDocumento)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("NO SE ENCONTROL EL DOCUMENTO: " + nombreArchivo);
            alert.showAndWait();


            return;
        }

        // Verificar que sea un PDF
        if (!nombreArchivo.toLowerCase().endsWith(".pdf")) {
            System.out.println("El archivo seleccionado no es un PDF.");
            return;
        }

        try {
            // Mostrar el documento en el visor
            visualizarPdf(rutaDocumento.toFile());

        } catch (IOException e) {
            System.err.println("Error al consultar el documento.");
            e.printStackTrace();
        }
    }

    public String generarUUID() {
        return UUID.randomUUID().toString();
    }


    public String generarHashSHA256(File archivo) throws IOException, NoSuchAlgorithmException {

        if (archivo == null || !archivo.isFile()) {
            throw new IllegalArgumentException("El archivo no existe o no es válido.");
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (FileInputStream fis = new FileInputStream(archivo)) {

            byte[] buffer = new byte[8192];
            int bytesLeidos;

            while ((bytesLeidos = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesLeidos);
            }
        }

        return HexFormat.of().formatHex(digest.digest());
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
        documentoSalida.setTemp(documento.getTemp());

        return documentoSalida;


    }


    @FXML
    private void cerrarDocumento(ActionEvent evento) throws IOException {

        cerrarVista(evento);

    }

}
