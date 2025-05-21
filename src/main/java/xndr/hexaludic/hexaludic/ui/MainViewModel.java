package xndr.hexaludic.hexaludic.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import xndr.hexaludic.hexaludic.dao.DaoPartidasImpl;
import xndr.hexaludic.hexaludic.domain.Partida;
import xndr.hexaludic.hexaludic.service.ServicioPartidasImpl;

public class MainViewModel {
    private final ServicioPartidasImpl servicioPartidas;
    private final ObservableList<Partida> partidas;

    private MainViewModel() {
        this(new ServicioPartidasImpl(new DaoPartidasImpl()));
    }

    public MainViewModel(ServicioPartidasImpl servicioPartidas) {
        this.servicioPartidas = servicioPartidas;
        partidas = FXCollections.observableArrayList(this.servicioPartidas.getListaPartidas());

    }
    public ObservableList<Partida> getAnimales() {
        return partidas;
    }

    public ServicioPartidasImpl getServicioPartidas() { return servicioPartidas; }
}
