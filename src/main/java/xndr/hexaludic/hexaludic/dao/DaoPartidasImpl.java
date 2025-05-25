package xndr.hexaludic.hexaludic.dao;

import xndr.hexaludic.hexaludic.common.GuardadoNoEncontradoException;
import xndr.hexaludic.hexaludic.common.PartidaDuplicadaException;
import xndr.hexaludic.hexaludic.domain.Partida;

import java.util.List;

public class DaoPartidasImpl implements DaoPartidas {

    private final Partidas partidas;

    public DaoPartidasImpl() {
        this.partidas = new Partidas();
    }

    public DaoPartidasImpl(Partidas partidas) {
        this.partidas = partidas;
    }

    @Override
    public List<Partida> getListaPartidas() {
        return partidas.getListaPartidas();
    }

    @Override
    public boolean removePartida(Partida partida) {
        return partidas.getListaPartidas().remove(partida);
    }

    @Override
    public boolean addPartida(Partida partida) {
        boolean existe = getPartida(partida);
        if (existe) {
            throw new PartidaDuplicadaException(partida.getId());
        }
        return partidas.getListaPartidas().add(partida);
    }

    @Override
    public boolean getPartida(Partida partida) {
        List<Partida> partidas = getListaPartidas();
        return partidas.stream().anyMatch(p -> p.getId() == partida.getId());
    }

    @Override
    public boolean getPartidaById(int id) {
        List<Partida> partidas = getListaPartidas();
        return partidas.stream().anyMatch(p -> p.getId() == id);
    }

    @Override
    public boolean updatePartida(Partida partida1, Partida partida2) {
        removePartida(partida1);
        addPartida(partida2);
        return getPartida(partida2);
    }

    @Override
    public void setPartidas(List<Partida> partidas) {
        this.partidas.setListaPartidas(partidas);
    }

    @Override
    public void cargarGuardado(String jugador) {
        Guardados guardados = new Guardados();
        List<Partida> partidas = guardados.loadPartidas(jugador);
        if (partidas == null || partidas.isEmpty()) {
            throw new GuardadoNoEncontradoException(jugador);
        }
        setPartidas(partidas);
    }

    @Override
    public boolean guardarGuardado(String jugador) {
        Guardados guardados = new Guardados();
        return guardados.savePartidas(getListaPartidas(), jugador);
    }
}
