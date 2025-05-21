package xndr.hexaludic.hexaludic.service;

import xndr.hexaludic.hexaludic.domain.Partida;

import java.io.IOException;
import java.util.List;

public interface ServicioPartidas {
    List<Partida> getListaPartidas();
    void removePartida(Partida partida);
    boolean addPartida(Partida partida);
    boolean getPartida(Partida partida);
    boolean updatePartida(Partida partida1, Partida partida2);
    void cargarGuardado(String jugador);
    void guardarGuardado(String jugador);
}
