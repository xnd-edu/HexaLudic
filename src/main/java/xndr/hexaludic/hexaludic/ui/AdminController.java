package xndr.hexaludic.hexaludic.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xndr.hexaludic.hexaludic.common.Config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private StackPane contentPane;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private String password = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar la contraseña desde el archivo de propiedades
        password = new Config().loadPasswordProperties();
        System.out.println("Password cargada: '" + password + "'");
        // log.info("Contraseña cargada: '" + password + "'");
    }


    @FXML
    protected void handleLogin() {
        String input = passwordField.getText();

        if (input.equals(password)) {
            try {
                ResourceBundle rb = ResourceBundle.getBundle("/xndr/hexaludic/hexaludic/textos", Locale.getDefault());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/xndr/hexaludic/hexaludic/fxml/admin-editor.fxml"), rb);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) passwordField.getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText("Contraseña incorrecta");
        }
    }

    @FXML
    protected void quitarAviso() {
        errorLabel.setVisible(false);
    }

    @FXML
    protected void handleGuardados() {
        mostrarArchivos(new Config().loadPathProperties());
    }

    @FXML
    protected void handleTableroOca() {
        mostrarArchivos(new Config().loadGamePathProperties());
    }

    private void mostrarArchivos(String path) {
        File dir = new File(path);
        File[] archivos = dir.listFiles(File::isFile);

        if (archivos == null || archivos.length == 0) {
            contentPane.getChildren().setAll(new Label("No hay archivos disponibles."));
            return;
        }

        ComboBox<File> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(archivos);
        comboBox.setPromptText("Selecciona un archivo");

        Button editarButton = new Button("Editar");
        editarButton.setOnAction(e -> {
            File seleccionado = comboBox.getValue();
            if (seleccionado != null) {
                editarArchivo(seleccionado);
            }
        });

        VBox layout = new VBox(10, comboBox, editarButton);
        contentPane.getChildren().setAll(layout);
    }


    private void editarArchivo(File seleccion) {

    }
}