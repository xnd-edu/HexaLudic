package xndr.hexaludic.hexaludic.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class HexaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        List<String> idiomas = List.of("Español", "English");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Español", idiomas);
        dialog.setTitle("Select your language");
        dialog.setHeaderText("Selecciona tu idioma / Select your language");
        dialog.setContentText("Idioma / Language:");

        Optional<String> resultado = dialog.showAndWait();

        Locale locale = resultado.map(idioma -> {
            switch (idioma) {
                case "English": return new Locale("en");
                case "Español": return new Locale("es");
                default: return Locale.getDefault(); // fallback
            }
        }).orElse(Locale.getDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(HexaApplication.class.getResource("/xndr/hexaludic/hexaludic/hexa-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("HexaLudic");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}