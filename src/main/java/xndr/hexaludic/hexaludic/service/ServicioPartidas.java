package xndr.hexaludic.hexaludic.service;

import xndr.hexaludic.hexaludic.domain.Partida;

import java.util.List;
import java.util.Map;

public interface ServicioPartidas {
    List<Partida> getListaPartidas();
    boolean removePartida(Partida partida);
    boolean addPartida(Partida partida);
    void setPartidas(List<Partida> partidas);
    boolean getPartida(Partida partida);
    boolean getPartidaById(int id);
    boolean updatePartida(Partida partida1, Partida partida2);
    List<Partida> cargarGuardado(String jugador);
    void guardarGuardado(String jugador);
    Map<String, Integer> getRankingVictorias();
    List<Partida> cargarGuardadotxt(String jugador);
    boolean guardarGuardadoTxt(String jugador);
}
