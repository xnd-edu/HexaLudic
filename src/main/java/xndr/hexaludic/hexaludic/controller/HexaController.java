package xndr.hexaludic.hexaludic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HexaController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}