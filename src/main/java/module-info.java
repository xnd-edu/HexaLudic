module xndr.hexaludic.hexaludic {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.google.gson;


    opens xndr.hexaludic.hexaludic to javafx.fxml;
    exports xndr.hexaludic.hexaludic;
    opens
}