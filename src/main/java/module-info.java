module com.unad.documentinspector {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.swing;


    opens com.unad.documentinspector to javafx.fxml;
    exports com.unad.documentinspector;
    exports com.unad.documentinspector.dto;
    opens com.unad.documentinspector.dto to javafx.fxml;
    opens com.unad.documentinspector.entidad;

}