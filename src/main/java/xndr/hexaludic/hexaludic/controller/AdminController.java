package xndr.hexaludic.hexaludic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import xndr.hexaludic.hexaludic.common.Config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/xndr/hexaludic/hexaludic/admin-menu.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/xndr/hexaludic/hexaludic/styles/admin.css").toExternalForm());
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

    }

    private void mostrarArchivos(String path) {
        File dir = new File(path);

        StringBuilder sb = new StringBuilder("Archivos encontrados:\n");
        Arrays.stream(dir.listFiles())
                .filter(File::isFile)
                .forEach(file -> sb.append("- ").append(file.getName()).append("\n"));

        contentPane.getChildren().clear();
        contentPane.getChildren().add(new Label(sb.toString()));
    }
}