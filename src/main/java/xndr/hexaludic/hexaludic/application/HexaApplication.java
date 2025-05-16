package xndr.hexaludic.hexaludic.application;

import javafx.application.Application;
import javafx.application.Platform;
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
        String opcion = "";
        
        ChoiceDialog<String> adminDialog = new ChoiceDialog<>("Play", "Play", "Administrate");
        adminDialog.setTitle("Role selection");
        adminDialog.setHeaderText("What do you want to do?");
        adminDialog.setContentText("Choose an option:");
        
        Optional<String> selected = adminDialog.showAndWait();
        if (selected.isEmpty()) {
            // Si el usuario cierra el diálogo sin elegir: salir de la app
            Platform.exit();
            return;
        }

        String selectedOption = selected.get();

        // 3. Decidir qué hacer según la opción
        if (selectedOption.equals("Play")) {
            List<String> idiomas = List.of("Español", "English");

            ChoiceDialog<String> langDialog = new ChoiceDialog<>("Español", idiomas);
            langDialog.setTitle("Select your language");
            langDialog.setHeaderText("Selecciona tu idioma / Select your language");
            langDialog.setContentText("Idioma / Language:");

            Optional<String> resultado = langDialog.showAndWait();
            if (resultado.isEmpty()) {
                Platform.exit();
                return;
            }

            Locale locale = resultado.map(idioma -> {
                switch (idioma) {
                    case "English": return new Locale("en");
                    case "Español": return new Locale("es");
                    default: return Locale.getDefault(); // fallback
                }
            }).orElse(Locale.getDefault());

            loadFXML("/xndr/hexaludic/hexaludic/hexa-view.fxml", stage);
        } else if (selectedOption.equals("Administrate")) {
            loadFXML("/xndr/hexaludic/hexaludic/admin-login.fxml", stage);
        }

    }

    public static void main(String[] args) {
        launch();
    }

    private void loadFXML(String fxmlFile, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}