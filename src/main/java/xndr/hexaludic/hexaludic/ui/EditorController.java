package xndr.hexaludic.hexaludic.ui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import xndr.hexaludic.hexaludic.domain.Partida;
import xndr.hexaludic.hexaludic.service.ServicioPartidas;

public class EditorController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MFXButton botonAdd;
    @FXML
    private MFXButton botonDelete;
    @FXML
    private MFXButton botonUpdate;
    @FXML
    private TableView<Partida> tablaAnimales;
    @FXML
    private TableColumn<Partida, String> columna1;
    @FXML
    private TableColumn<Partida, Integer> columna2;
    @FXML
    private TableColumn<Partida, String> columna3;
    @FXML
    private TableColumn<Partida, String> columna4;
    @FXML
    private MFXComboBox<String> comboBox;
    @FXML
    private MFXTextField nombre;
    @FXML
    private MFXTextField edad;
    @FXML
    private MFXTextField id;
    @FXML
    private Label label;
    @FXML
    private MFXToggleButton toggleidioma;

    @FXML
    private MFXToggleButton modooscuro;

    public ServicioPartidas getServicioAnimales() { return servicioPartidas; }

}
