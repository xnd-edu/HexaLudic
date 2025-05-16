package xndr.hexaludic.hexaludic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import xndr.hexaludic.hexaludic.common.Config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    protected void handleLogin() {
        String input = passwordField.getText();
        String password = new Config().loadPasswordProperties();
        System.out.println("Password cargada: '" + password + "'");
        // log.info("Contraseña cargada: '" + password + "'");


        if (input.equals(password)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/xndr/hexaludic/hexaludic/admin-menu.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene adminScene = new Scene(root);
            Stage stage = (Stage) passwordField.getScene().getWindow();
            stage.setScene(adminScene);
            stage.show();
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText("The password is incorrect.");
        }
    }
}