package xndr.hexaludic.hexaludic.ui;

import io.github.palexdev.materialfx.controls.*;
import javafx.beans.property.SimpleStringProperty;
import xndr.hexaludic.hexaludic.common.Config;
import xndr.hexaludic.hexaludic.common.GuardadoNoEncontradoException;
import xndr.hexaludic.hexaludic.common.PartidaDuplicadaException;
import xndr.hexaludic.hexaludic.dao.DaoPartidasImpl;
import xndr.hexaludic.hexaludic.dao.DaoTableroOcaImpl;
import xndr.hexaludic.hexaludic.domain.Partida;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import xndr.hexaludic.hexaludic.service.ServicioPartidasImpl;
import xndr.hexaludic.hexaludic.service.ServicioTableroOcaImpl;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EditorController implements Initializable {

    private final MainViewModel viewModel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MFXComboBox<String> fileChooser;
    @FXML
    private MFXButton botonAdd;
    @FXML
    private MFXButton botonDelete;
    @FXML
    private MFXButton botonUpdate;
    @FXML
    private TableView<Partida> tablaPartidas;
    @FXML
    private TableColumn<Partida, String> columna1;
    @FXML
    private TableColumn<Partida, String> columna2;
    @FXML
    private TableColumn<Partida, String> columna3;
    @FXML
    private TableColumn<Partida, String> columna4;
    @FXML
    private TableColumn<Partida, String> columna5;

    @FXML
    private MFXComboBox<String> comboBox;
    @FXML
    private MFXTextField fechaPartida;
    @FXML
    private MFXTextField jugador2;
    @FXML
    private MFXComboBox<String> resultado;
    @FXML
    private MFXTextField juego;
    @FXML
    private MFXTextField id;
    @FXML
    private Label label;
    @FXML
    private MFXButton toggleidioma;
    @FXML
    private MFXButton quit;
    @FXML
    private MFXButton botonGuardar;
    @FXML
    private MFXButton botonVaciar;

    private boolean english = false;

    public EditorController() {
        DaoPartidasImpl daoPartidas = new DaoPartidasImpl();
        ServicioPartidasImpl servicioPartidas = new ServicioPartidasImpl(daoPartidas);
        ServicioTableroOcaImpl servicioTablero = new ServicioTableroOcaImpl(new DaoTableroOcaImpl());
        viewModel = new MainViewModel(servicioPartidas, servicioTablero);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaPartidas.setItems(viewModel.getPartidas());
        columna1.setCellValueFactory(new PropertyValueFactory<>("id"));
        columna2.setCellValueFactory(new PropertyValueFactory<>("juego"));
        columna3.setCellValueFactory(cellData -> {
            boolean victoria = cellData.getValue().isVictoria();
            return new SimpleStringProperty(victoria ? "Win" : "Lose");
        });
        columna4.setCellValueFactory(new PropertyValueFactory<>("jugador2"));
        columna5.setCellValueFactory(cellData -> {
            LocalDateTime fecha = cellData.getValue().getFechaPartida();
            String fechaStr = fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "—";
            return new SimpleStringProperty(fechaStr);
        });

        // Resto del código sigue igual...
        resultado.getItems().addAll(resourceBundle.getString("combo1"), resourceBundle.getString("combo2"));

        tablaPartidas.setOnMouseClicked((MouseEvent event) -> onEdit());

        applyHoverScale(botonAdd, 1.1, 150);
        applyHoverScale(botonDelete, 1.1, 150);
        applyHoverScale(botonUpdate, 1.1, 150);
        applyHoverScale(toggleidioma, 1.1, 150);
        applyHoverScale(quit, 1.1, 150);
        applyHoverScale(botonGuardar, 1.1, 150);
        applyHoverScale(botonVaciar, 1.1, 150);

        initializeFileChooser();
    }


    private void initializeFileChooser() {
        File folder = new File(new Config().loadPathProperties());
        if (folder.exists() && folder.isDirectory()) {
            Optional.ofNullable(folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json")))
                    .ifPresent(files -> Arrays.stream(files)
                            .map(File::getName)
                            .forEach(fileChooser.getItems()::add));
        }
    }

    public void onEdit() {
        //check si se ha seleccionado un elemento y actualiza los textField con los valores de los atributos del elemento seleccionado
        //Con esto no haría falta la imagen de ayuda puesto que el usuario no tendría que introducirlo en los textField
        if (tablaPartidas.getSelectionModel().getSelectedItem() != null) {
            Partida selectedPartida = tablaPartidas.getSelectionModel().getSelectedItem();
            String resultadoStr = "";
            if (selectedPartida.isVictoria())
                resultadoStr = "Win";
            else
                resultadoStr = "Lose";

            id.setText(String.valueOf(selectedPartida.getId()));
            juego.setText(selectedPartida.getJuego());
            resultado.setText(resultadoStr);
            jugador2.setText(selectedPartida.getJugador2());
            fechaPartida.setText(selectedPartida.getFechaPartida().toString());
        }
    }

    @FXML
    private void cambioIdioma() {
        ResourceBundle bundle;
        english = !english;
        if (english) {
            bundle = ResourceBundle.getBundle("/xndr/hexaludic/hexaludic/textos", Locale.ENGLISH);
        } else {
            bundle = ResourceBundle.getBundle("/xndr/hexaludic/hexaludic/textos", Locale.getDefault());
        }
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("xndr/hexaludic/hexaludic/fxml/admin-editor.fxml"), bundle);
        label.setText(bundle.getString("titulo"));
        columna1.setText(bundle.getString("columnaId"));
        columna2.setText(bundle.getString("columnaJuego"));
        columna3.setText(bundle.getString("columnaResultado"));
        columna4.setText(bundle.getString("columnaJugador2"));
        columna5.setText(bundle.getString("columnaFecha"));
        botonAdd.setText(bundle.getString("botonAdd"));
        botonDelete.setText(bundle.getString("botonDelete"));
        botonUpdate.setText(bundle.getString("botonUpdate"));
        id.setPromptText(bundle.getString("columnaId"));
        juego.setPromptText(bundle.getString("columnaJuego"));
        resultado.setPromptText(bundle.getString("columnaResultado"));
        jugador2.setPromptText(bundle.getString("columnaJugador2"));
        fechaPartida.setPromptText(bundle.getString("columnaFecha"));
        toggleidioma.setText(bundle.getString("idioma"));
        resultado.getItems().clear();
        resultado.getItems().addAll(bundle.getString("combo1"), bundle.getString("combo2"));
    }

    @FXML
    private void handleGuardados() {
        String selectedFile = fileChooser.getValue();
        if (selectedFile != null && selectedFile.endsWith(".json")) {
            String jugador = selectedFile.substring(0, selectedFile.indexOf(".json"));
            try {
                viewModel.recargarPartidas(jugador);
                System.out.println("Nuevas partidas: " + viewModel.getPartidas());
            } catch (GuardadoNoEncontradoException e) {
                mostrarAlertaGuardadoNoEncontrado(jugador);
            }
        }
    }


    @FXML
    private void addPartida() {
        LocalDateTime fecha = stringToLocalDateTime(fechaPartida.getText());

        if (id.getText().isEmpty() || juego.getText().isEmpty() || resultado.getValue().isEmpty() || jugador2.getText().isEmpty() || fechaPartida.getText().isEmpty()) {
            alertaErrorAddPartida();
        } else {
            try {
                Partida partida = new Partida(
                        Integer.parseInt(id.getText()),
                        juego.getText(),
                        Boolean.parseBoolean(resultado.getText()),
                        jugador2.getText(),
                        fecha
                );

                if (viewModel.getServicioPartidas().addPartida(partida)) {
                    tablaPartidas.getItems().add(partida);
                    alertaOKAddPartida();
                    limpiarCampos();
                } else {
                    alertaErrorAddPartida();
                }
            } catch (PartidaDuplicadaException e) {
                alertaPartidaDuplicada(Integer.parseInt(id.getText()));
            } catch (Exception e) {
                e.printStackTrace(); // Útil para depuración
                alertaErrorAddPartida();
            }
        }
    }


    @FXML
    private void deletePartida() {
        Partida partida = tablaPartidas.getSelectionModel().getSelectedItem();
        if (partida != null) {
            if (alertaConfirmationDeletePartida(partida)) {
                if (viewModel.getServicioPartidas().removePartida(partida)) {
                    alertaOkDeletePartida();
                    limpiarCampos();
                } else {
                    alertaErrorDeletePartida();
                }
            }
        } else {
            alertaErrorDeletePartida();
        }
    }

    @FXML
    private void updatePartida() {
        LocalDateTime fecha = stringToLocalDateTime(fechaPartida.getText());
        if (id.getText().isEmpty() || juego.getText().isEmpty() || resultado.getValue().isEmpty() || jugador2.getText().isEmpty() || fechaPartida.getText().isEmpty()) {
            alertaErrorUpdatePartida();
        } else {
            Partida partida1 = new Partida(Integer.parseInt(id.getText()), juego.getText(), Boolean.parseBoolean(resultado.getText()), jugador2.getText(), fecha);
            Partida partida2 = tablaPartidas.getSelectionModel().getSelectedItem();
            if (viewModel.getServicioPartidas().updatePartida(partida1, partida2)) {
                tablaPartidas.getItems().remove(partida2);
                tablaPartidas.getItems().add(partida1);
                alertaOKUpdatePartida();
                limpiarCampos();
                tablaPartidas.refresh();
            } else {
                alertaErrorUpdatePartida();
            }
        }
    }

    private void limpiarCampos() {
        id.clear();
        juego.clear();
        resultado.clear();
        jugador2.clear();
        fechaPartida.clear();
    }

    @FXML
    private void ayuda() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Ayuda");
        a.setHeaderText("Ayuda");
        a.setContentText("Selecciona el partida a actualizar en la tabla e introduce los nuevos datos");
        a.show();
    }

    @FXML
    private void guardarPartidas() {
        if (alertaConfirmationGuardar()) {
            String selectedFile = fileChooser.getValue();
            String jugador = selectedFile.substring(0, selectedFile.indexOf(".json"));
            viewModel.getServicioPartidas().guardarGuardado(jugador);
        }
    }

    @FXML
    private void vaciarPartidas() {
        if (alertaConfirmationVaciarPartidas())
            viewModel.getServicioPartidas().setPartidas(List.of());
    }

    @FXML
    private void salir() {

    }


    private void alertaErrorAddPartida() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al añadir el partida");
        alert.setContentText("No se ha podido añadir la partida");
        alert.show();
    }

    private void alertaPartidaDuplicada(int id) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ID duplicado");
        alert.setHeaderText("No se puede añadir la partida");
        alert.setContentText("Ya existe una partida con el ID: " + id);
        alert.show();
    }

    private void mostrarAlertaGuardadoNoEncontrado(String jugador) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Guardado no encontrado");
        alert.setContentText("No se encontró ningún archivo de guardado para el jugador: " + jugador);
        alert.show();
    }


private void alertaOKAddPartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Partida añadida correctamente");
        alert.setContentText("Se ha añadido correctamente");
        alert.show();

    }

    private void alertaOKUpdatePartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Partida actualizada con éxito");
        alert.setContentText("Se ha actualizado correctamente");
        alert.show();
    }


    private void alertaErrorUpdatePartida() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al actualizar partida");
        alert.setContentText("Problemas al actualizar el partida");
        alert.show();
    }

    private boolean alertaConfirmationDeletePartida(Partida partida) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de Confirmación");
        alert.setHeaderText("Eliminar partida");
        alert.setContentText("Confirma el borrado de " + partida + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tablaPartidas.getItems().remove(partida);
            return true;
        }
        return false;
    }

    private boolean alertaConfirmationVaciarPartidas() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Diálogo de Confirmación");
        alert.setHeaderText("Vaciar partidas");
        alert.setContentText("¿Está seguro de que desea borrar todas las partidas?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private boolean alertaConfirmationGuardar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de Confirmación");
        alert.setHeaderText("Guardar los cambios");
        alert.setContentText("¿Desea guardar los cambios?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private boolean alertaConfirmationSalir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de Confirmación");
        alert.setHeaderText("Salir");
        alert.setContentText("¿Desea salir de la aplicación? Se perderán los cambios no guardados.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void alertaOkDeletePartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Partida eliminada con éxito");
        alert.setContentText("Se ha eliminado correctamente");
        alert.show();
    }


    private void alertaErrorDeletePartida() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al eliminar el partida");
        alert.setContentText("No se ha podido eliminar el partida");
        alert.show();
    }

    private LocalDateTime stringToLocalDateTime(String string) {
        try {
            return LocalDateTime.parse(string);
        } catch (Exception e) {
            System.out.println("Fecha inválida: " + e.getMessage());
            return null;
        }
    }


    private void applyHoverScale(javafx.scene.Node node, double scale, int durationMs) {
        javafx.animation.ScaleTransition stEnter = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(durationMs), node);
        stEnter.setToX(scale);
        stEnter.setToY(scale);

        javafx.animation.ScaleTransition stExit = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(durationMs), node);
        stExit.setToX(1.0);
        stExit.setToY(1.0);

        node.setOnMouseEntered(e -> {
            stExit.stop();
            stEnter.playFromStart();
        });

        node.setOnMouseExited(e -> {
            stEnter.stop();
            stExit.playFromStart();
        });
    }

}