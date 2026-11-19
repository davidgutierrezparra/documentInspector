package com.unad.documentinspector;

import com.unad.documentinspector.dto.DocumentosDto;
import com.unad.documentinspector.entidad.Documento;
import com.unad.documentinspector.entidad.EstadoDocumento;
import com.unad.documentinspector.entidad.HistorialFirma;
import com.unad.documentinspector.repositorio.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.Optional;

public class AprobarDocumentos {

    private DocumentosDto documento;

    @FXML
    private VBox contenedorPaginas;

    @FXML
    public AnchorPane aprobarDocumento;

    private final HistorialFirmaRepository historialFirmaRepository;
    private final DocumentoRepository documentoRepository;
    private final FuncionalidadesController funcionalidadesController;


    public AprobarDocumentos() {

        this.historialFirmaRepository = new HistorialFirmaRepository();
        this.documentoRepository = new DocumentoRepository();
        this.funcionalidadesController = new FuncionalidadesController();
    }

    public void setDocumento(DocumentosDto documento) {
        this.documento = documento;
        consultarDocumento(documento.getCodigo());
    }


    @FXML
    private void aprobar(ActionEvent evento) throws IOException {


        consultarDocumentoEditar(documento.getCodigo());

        Optional<HistorialFirma> historial = historialFirmaRepository.findById(documento.getTemp());

        LocalDateTime fechaAprueba = LocalDateTime.now();
        historial.get().setCodigoFirma(generarCodigoFirma(fechaAprueba, documento.getTemp()));
        historial.get().setFechaCrea(fechaAprueba);
        historialFirmaRepository.update(historial.get());
        Optional<Documento> documentoConsulta = documentoRepository.findById(historial.get().getDocumento().getIdDocumento());
        documentoConsulta.get().setEstadoDocumento(new EstadoDocumento(3L));
        documentoConsulta.get().setHashFinal(consultarHash(documento.getCodigo()));
        documentoRepository.update(documentoConsulta.get());

        Stage stage = (Stage) aprobarDocumento.getScene().getWindow();
        stage.close();


    }


    private void consultarDocumento(String nombreArchivo) {

        Path rutaDocumento = Paths.get(
                "C:\\GestionDocumental\\Documentos",
                nombreArchivo
        );

        if (!Files.exists(rutaDocumento) || !Files.isRegularFile(rutaDocumento)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("NO SE ENCONTROL EL DOCUMENTO: " + nombreArchivo);
            alert.showAndWait();
            return;
        }

        if (!nombreArchivo.toLowerCase().endsWith(".pdf")) {
            System.out.println("El archivo seleccionado no es un PDF.");
            return;
        }
        try {
            visualizarPdf(rutaDocumento.toFile());
        } catch (IOException e) {
            System.err.println("Error al consultar el documento.");
            e.printStackTrace();
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


    private void consultarDocumentoEditar(String nombreArchivo) {

        Path rutaDocumento = Paths.get("C:\\GestionDocumental\\Documentos", nombreArchivo);

        if (!Files.exists(rutaDocumento) || !Files.isRegularFile(rutaDocumento)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("NO SE ENCONTRÓ EL DOCUMENTO: " + nombreArchivo);
            alert.showAndWait();
            return;
        }

        if (!nombreArchivo.toLowerCase().endsWith(".pdf")) {
            System.out.println("El archivo seleccionado no es un PDF.");
            return;
        }

        try {

            File documentoModificado = marcarAprobacionPdf(rutaDocumento.toFile());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("DOCUMENTO APROBADO EXITOSAMENTE.");
            alert.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DOCUMENT INSPECTOR");
            alert.setHeaderText(null);
            alert.setContentText("ERROR AL APROBAR EL DOCUMENTO.");
            alert.showAndWait();
        }
    }


    public File marcarAprobacionPdf(File archivoOriginal) throws IOException {

        if (archivoOriginal == null || !archivoOriginal.isFile()) {
            throw new IllegalArgumentException("El archivo PDF no existe.");
        }

        Path rutaOriginal = archivoOriginal.toPath();
        Path archivoTemporal = Files.createTempFile(rutaOriginal.getParent(), "documento_", ".pdf");

        try {

            try (PDDocument documento = Loader.loadPDF(archivoOriginal)) {

                PDType1Font fuente = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

                // Textos que se agregarán al PDF
                String[] textos = {
                        "DOCUMENT INSPECTOR: APRUEBA",
                        SesionUsuario.getUsuario().getNombres(),
                        LocalDate.now().toString()
                };

                // Tamaños de fuente
                float[] tamanios = {6, 8, 6};

                // Margen superior y derecho
                float margenSuperior = 25;
                float margenDerecho = 35;

                for (PDPage pagina : documento.getPages()) {

                    float alto = pagina.getMediaBox().getHeight();

                    // Un único flujo para escribir los tres textos
                    try (PDPageContentStream contenido = new PDPageContentStream(
                            documento, pagina,
                            PDPageContentStream.AppendMode.APPEND, true, true)) {

                        for (int i = 0; i < textos.length; i++) {

                            float tamanio = tamanios[i];

                            float anchoTexto = fuente.getStringWidth(textos[i]) / 1000 * tamanio;

                            float posicionX = pagina.getMediaBox().getWidth() - anchoTexto - margenDerecho;
                            float posicionY = alto - margenSuperior - tamanio;

                            contenido.beginText();
                            contenido.setFont(fuente, tamanio);
                            contenido.setNonStrokingColor(Color.RED);
                            contenido.newLineAtOffset(posicionX, posicionY);
                            contenido.showText(textos[i]);
                            contenido.endText();

                            // Separación vertical entre los textos
                            margenSuperior += tamanio + 3;
                        }
                    }
                }

                documento.save(archivoTemporal.toFile());
            }

//            try (PDDocument documento = Loader.loadPDF(archivoOriginal)) {
//
//                PDType1Font fuente = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
//
//
//                //Datos de documentInspector
//                String document = "DOCUMENT INSPECTOR: APRUEBA";
//                float tamanioD = 6;
//                float margenD = 25;
//
//                for (PDPage pagina : documento.getPages()) {
//
//                    float ancho = pagina.getMediaBox().getWidth();
//                    float alto = pagina.getMediaBox().getHeight();
//
//                    float anchoTexto = fuente.getStringWidth(document) / 1000 * tamanioD;
//
//                    float posicionX = ancho - anchoTexto - margenD;
//                    float posicionY = alto - margenD - tamanioD;
//
//                    try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina, PDPageContentStream.AppendMode.APPEND, true, true)) {
//                        contenido.beginText();
//                        contenido.setFont(fuente, tamanioD);
//                        contenido.setNonStrokingColor(Color.RED);
//                        contenido.newLineAtOffset(posicionX, posicionY);
//                        contenido.showText(document);
//                        contenido.endText();
//                    }
//                }
//
//
//                //Datos de aprueba en documento
//                String aprueba = SesionUsuario.getUsuario().getNombres();
//                float tamanio = 8;
//                float margen = 35;
//
//                for (PDPage pagina : documento.getPages()) {
//
//                    float ancho = pagina.getMediaBox().getWidth();
//                    float alto = pagina.getMediaBox().getHeight();
//
//                    float anchoTexto = fuente.getStringWidth(aprueba) / 1000 * tamanio;
//
//                    float posicionX = ancho - anchoTexto - margen;
//                    float posicionY = alto - margen - tamanio;
//
//                    try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina, PDPageContentStream.AppendMode.APPEND, true, true)) {
//                        contenido.beginText();
//                        contenido.setFont(fuente, tamanio);
//                        contenido.setNonStrokingColor(Color.RED);
//                        contenido.newLineAtOffset(posicionX, posicionY);
//                        contenido.showText(aprueba);
//                        contenido.endText();
//                    }
//                }
//
//
//                //Datos de fecha en documento
//                String fechAprueba = LocalDate.now().toString();
//                float tamanioF = 6;
//                float margenF = 45;
//
//                for (PDPage pagina : documento.getPages()) {
//
//                    float ancho = pagina.getMediaBox().getWidth();
//                    float alto = pagina.getMediaBox().getHeight();
//
//                    float anchoTexto = fuente.getStringWidth(fechAprueba) / 1000 * tamanioF;
//
//                    float posicionX = ancho - anchoTexto - margenF;
//                    float posicionY = alto - margenF - tamanioF;
//
//                    try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina, PDPageContentStream.AppendMode.APPEND, true, true)) {
//                        contenido.beginText();
//                        contenido.setFont(fuente, tamanioF);
//                        contenido.setNonStrokingColor(Color.RED);
//                        contenido.newLineAtOffset(posicionX, posicionY);
//                        contenido.showText(fechAprueba);
//                        contenido.endText();
//                    }
//                }
//
//                documento.save(archivoTemporal.toFile());
//            }

            Files.move(archivoTemporal, rutaOriginal, StandardCopyOption.REPLACE_EXISTING);
            return archivoOriginal;

        } finally {
            Files.deleteIfExists(archivoTemporal);
        }
    }

    public long generarCodigoFirma(LocalDateTime fechaHora, long valor) {

        String fecha = fechaHora.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        long suma = fecha.chars()
                .map(Character::getNumericValue)
                .sum();

        return suma * valor;
    }


    private String consultarHash(String nombreArchivo) {

        Path rutaDocumento = Paths.get("C:\\GestionDocumental\\Documentos", nombreArchivo);

        try {
            return generarHashSHA256(rutaDocumento.toFile());
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Error al consultar el documento.");
            e.printStackTrace();
        }
        return null;
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


    @FXML
    private void cerrar() throws IOException {

        Stage stage = (Stage) aprobarDocumento.getScene().getWindow();
        stage.close();
    }


}
