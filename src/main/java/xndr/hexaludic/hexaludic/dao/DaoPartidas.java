package xndr.hexaludic.hexaludic.dao;

import xndr.hexaludic.hexaludic.domain.Partida;

import java.io.IOException;
import java.util.List;

public interface DaoPartidas {
    List<Partida> getListaPartidas();
    boolean removePartida(Partida partida);
    boolean addPartida(Partida partida);
    boolean getPartida(Partida partida);
    boolean getPartidaById(int id);
    boolean updatePartida(Partida partida1, Partida partida2);
    void setPartidas(List<Partida> partidas);
    List<Partida> cargarGuardado(String jugador);
    boolean guardarGuardado(String jugador);
}
