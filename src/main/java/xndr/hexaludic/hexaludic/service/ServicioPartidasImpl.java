package xndr.hexaludic.hexaludic.service;

import xndr.hexaludic.hexaludic.dao.DaoPartidas;
import xndr.hexaludic.hexaludic.dao.DaoPartidasImpl;
import xndr.hexaludic.hexaludic.domain.Partida;

import java.util.List;

public class ServicioPartidasImpl implements ServicioPartidas {

    private final DaoPartidasImpl daoPartidas;

    public ServicioPartidasImpl() {
        this.daoPartidas = new DaoPartidasImpl();
    }

    public ServicioPartidasImpl(DaoPartidasImpl daoPartidas) {
        this.daoPartidas = daoPartidas;
    }

    @Override
    public List<Partida> getListaPartidas() {
        return daoPartidas.getListaPartidas();
    }

    @Override
    public boolean removePartida(Partida partida) {
        return daoPartidas.removePartida(partida);
    }

    @Override
    public boolean addPartida(Partida partida) {
        return daoPartidas.addPartida(partida);
    }

    @Override
    public void setPartidas(List<Partida> partidas) {
        daoPartidas.setPartidas(partidas);
    }

    @Override
    public boolean getPartida(Partida partida) {
        return daoPartidas.getPartida(partida);
    }

    @Override
    public boolean getPartidaById(int id) {
        return daoPartidas.getPartidaById(id);
    }

    @Override
    public boolean updatePartida(Partida partida1, Partida partida2) {
        return daoPartidas.updatePartida(partida1, partida2);
    }

    @Override
    public List<Partida> cargarGuardado(String jugador) {
        return daoPartidas.cargarGuardado(jugador);
    }

    @Override
    public void guardarGuardado(String jugador) {
        daoPartidas.guardarGuardado(jugador);
    }
}
