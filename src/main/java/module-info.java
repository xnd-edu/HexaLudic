module xndr.hexaludic.hexaludic {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires com.google.gson;
    requires org.apache.logging.log4j;

    exports xndr.hexaludic.hexaludic.ui;

    opens xndr.hexaludic.hexaludic to javafx.fxml;
    opens xndr.hexaludic.hexaludic.ui to javafx.fxml;
    opens xndr.hexaludic.hexaludic.dao to com.google.gson;
    opens xndr.hexaludic.hexaludic.domain to com.google.gson;
}