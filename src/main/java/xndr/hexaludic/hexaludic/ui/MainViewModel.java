package xndr.hexaludic.hexaludic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import xndr.hexaludic.hexaludic.dao.DaoPartidasImpl;
import xndr.hexaludic.hexaludic.dao.DaoTableroOcaImpl;
import xndr.hexaludic.hexaludic.domain.Casilla;
import xndr.hexaludic.hexaludic.domain.Partida;
import xndr.hexaludic.hexaludic.service.ServicioPartidasImpl;
import xndr.hexaludic.hexaludic.service.ServicioTableroOca;
import xndr.hexaludic.hexaludic.service.ServicioTableroOcaImpl;

public class MainViewModel {
    private final ServicioPartidasImpl servicioPartidas;
    private final ServicioTableroOca servicioTableroOca;
    private final ObservableList<Partida> partidas;
    private final ObservableList<Casilla> tablero;

    private MainViewModel() {
        this(
                new ServicioPartidasImpl(new DaoPartidasImpl()),
                new ServicioTableroOcaImpl(new DaoTableroOcaImpl())
        );
    }


    public MainViewModel(ServicioPartidasImpl servicioPartidas, ServicioTableroOcaImpl servicioTableroOca) {
        this.servicioPartidas = servicioPartidas;
        this.servicioTableroOca = servicioTableroOca;
        partidas = FXCollections.observableArrayList(this.servicioPartidas.getListaPartidas());
        tablero = FXCollections.observableArrayList(this.servicioTableroOca.getListaCasillas());
    }

    public ObservableList<Partida> getPartidas() {
        return partidas;
    }

    public ObservableList<Casilla> getTablero() { return tablero; }

    public ServicioPartidasImpl getServicioPartidas() { return servicioPartidas; }

    public ServicioTableroOca getServicioTableroOca() {
        return servicioTableroOca;
    }
}
