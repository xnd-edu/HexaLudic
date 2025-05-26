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
                stage.setTitle("HexaLudic");
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

}