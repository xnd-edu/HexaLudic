package xndr.hexaludic.hexaludic.ui;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import xndr.hexaludic.hexaludic.dao.DaoPartidas;
import xndr.hexaludic.hexaludic.dao.DaoPartidasImpl;
import xndr.hexaludic.hexaludic.dao.DaoTableroOcaImpl;
import xndr.hexaludic.hexaludic.domain.Partida;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import xndr.hexaludic.hexaludic.service.ServicioPartidas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import xndr.hexaludic.hexaludic.service.ServicioPartidasImpl;
import xndr.hexaludic.hexaludic.service.ServicioTableroOcaImpl;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

    private final MainViewModel viewModel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MFXButton botonAdd;
    @FXML
    private MFXButton botonDelete;
    @FXML
    private MFXButton botonUpdate;
    @FXML
    private MFXTableView<Partida> tablaPartidas;
    @FXML
    private MFXTableColumn<Partida> columna1;
    @FXML
    private MFXTableColumn<Partida> columna2;
    @FXML
    private MFXTableColumn<Partida> columna3;
    @FXML
    private MFXTableColumn<Partida> columna4;
    @FXML
    private MFXTableColumn<Partida> columna5;

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
    private MFXToggleButton toggleidioma;

    @FXML
    private MFXToggleButton modooscuro;

    public EditorController() {
        viewModel = new MainViewModel(new ServicioPartidasImpl(new DaoPartidasImpl()), new ServicioTableroOcaImpl(new DaoTableroOcaImpl()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaPartidas.setItems(viewModel.getPartidas());
        //mapeo de los atributos a las columnas
        columna1.setRowCellFactory(partida -> new MFXTableRowCell<>(Partida::getId));
        columna2.setRowCellFactory(partida -> new MFXTableRowCell<>(Partida::getJuego));
        columna3.setRowCellFactory(partida -> new MFXTableRowCell<>(p -> p.isVictoria() ? "Win" : "Lose"));
        columna4.setRowCellFactory(partida -> new MFXTableRowCell<>(Partida::getJugador2));
        columna5.setRowCellFactory(partida -> new MFXTableRowCell<>(p ->
                p.getFechaPartida().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        ));
        resultado.getItems().addAll(resourceBundle.getString("combo1"), resourceBundle.getString("combo2"));
        //si queremos que al seleccionar un elemento de la tabla se rellenen los textField hay que añadir un listener a la tabla para que
        //ejecute el método onEdit cada vez que ocurra..
        tablaPartidas.setOnMouseClicked((MouseEvent event) -> {
            onEdit();
        });
    }

    public void onEdit() {
        //check si se ha seleccionado un elemento y actualiza los textField con los valores de los atributos del elemento seleccionado
        //Con esto no haría falta la imagen de ayuda puesto que el usuario no tendría que introducirlo en los textField
        if (tablaPartidas.getSelectionModel().getSelectedValue() != null) {
            Partida selectedPartida = tablaPartidas.getSelectionModel().getSelectedValue();
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
        if (toggleidioma.isSelected()) {
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
        modooscuro.setText(bundle.getString("modooscuro"));
        comboBox.getItems().clear();
        comboBox.getItems().addAll(bundle.getString("combo1"), bundle.getString("combo2"));
    }

    @FXML
    private void modoOscuro() {
        if (modooscuro.isSelected()) {
            toggleidioma.setTextFill(Color.WHITE);
            modooscuro.setTextFill(Color.WHITE);
            anchorPane.setStyle("-fx-background-color: #000000;");
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-background-color: #000000");
            botonAdd.setStyle("-fx-text-fill: white; -fx-background-color: #4B0082;");
            botonDelete.setStyle("-fx-text-fill: white; -fx-background-color: #4B0082;");
            botonUpdate.setStyle("-fx-text-fill: white; -fx-background-color: #4B0082;");
        } else {
            toggleidioma.setTextFill(Color.BLACK);
            modooscuro.setTextFill(Color.BLACK);
            anchorPane.setStyle("-fx-background-color: #ffffff;");
            label.setTextFill(Color.BLACK);
            label.setStyle("-fx-background-color: #ffffff");
            botonAdd.setStyle("-fx-text-fill: black; -fx-background-color: #e6e9eb;");
            botonDelete.setStyle("-fx-text-fill: black; -fx-background-color: #e6e9eb;");
            botonUpdate.setStyle("-fx-text-fill: black; -fx-background-color: #e6e9eb;");
        }
    }

    @FXML
    private void addPartida() {
        LocalDateTime fecha = stringToLocalDateTime(fechaPartida.getText());
        if (id.getText().isEmpty() || juego.getText().isEmpty() || resultado.getValue().isEmpty() || jugador2.getText().isEmpty() || fechaPartida.getText().isEmpty()) {
            alertaErrorAddPartida();
        } else {
            Partida partida = new Partida(Integer.parseInt(id.getText()), juego.getText(), Boolean.parseBoolean(resultado.getText()), jugador2.getText(), fecha);
            if (viewModel.getServicioPartidas().addPartida(partida)) {
                tablaPartidas.getItems().add(partida);
                alertaOKAddPartida();
                limpiarCampos();
            } else {
                alertaErrorAddPartida();
            }
        }
    }

    @FXML
    private void deletePartida() {
        Partida partida = tablaPartidas.getSelectionModel().getSelectedValue();
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
            Partida partida2 = tablaPartidas.getSelectionModel().getSelectedValue();
            if (viewModel.getServicioPartidas().updatePartida(partida1, partida2)) {
                tablaPartidas.getItems().remove(partida2);
                tablaPartidas.getItems().add(partida1);
                alertaOKUpdatePartida();
                limpiarCampos();
                tablaPartidas.update();
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
        a.setContentText("Selecciona el animal a actualizar en la tabla e introduce los nuevos datos");
        a.show();
    }


    private void alertaErrorAddPartida() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al añadir el animal");
        alert.setContentText("No se ha podido añadir el animal");
        alert.show();
    }

    private void alertaOKAddPartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Partida añadido correctamente");
        alert.setHeaderText("Partida añadido correctamente");
        alert.setContentText("Se ha añadido correctamente");
        alert.show();

    }

    private void alertaOKUpdatePartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Partida actualizado con éxito");
        alert.setHeaderText("Partida actualizado con éxito");
        alert.setContentText("Se ha actualizado correctamente");
        alert.show();
    }


    private void alertaErrorUpdatePartida() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al actualizar animal");
        alert.setContentText("Problemas al actualizar el animal");
        alert.show();
    }

    private boolean alertaConfirmationDeletePartida(Partida animal) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de Confirmación");
        alert.setHeaderText("Diálogo confirmación");
        alert.setContentText("Confirma el borrado de " + animal + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tablaPartidas.getItems().remove(animal);
            return true;
        }
        return false;
    }

    private void alertaOkDeletePartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Partida eliminada con éxito");
        alert.setHeaderText("Partida eliminada con éxito");
        alert.setContentText("Se ha eliminado correctamente");
        alert.show();
    }


    private void alertaErrorDeletePartida() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al eliminar el animal");
        alert.setContentText("No se ha podido eliminar el animal");
        alert.show();
    }

    private LocalDateTime stringToLocalDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MM::uuuu HH::mm::ss").withLocale(Locale.ENGLISH);

        try {
            return LocalDateTime.parse(string, formatter);
        } catch (Exception e) {
            System.out.println("Fecha inválida: " + e.getMessage());
        }

        return null;
    }

}