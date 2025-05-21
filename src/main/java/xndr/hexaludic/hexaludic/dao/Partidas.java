package xndr.hexaludic.hexaludic.dao;

import lombok.Data;
import lombok.Setter;
import xndr.hexaludic.hexaludic.domain.Partida;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Partidas {
    private final List<Partida> listaPartidas;

    public Partidas(List<Partida> listaPartidas) {
        this.listaPartidas = listaPartidas;
    }

    public Partidas() {
        this.listaPartidas = new ArrayList<Partida>();

        listaPartidas.add(new Partida(1, "Oca", true, "Arthur", LocalDateTime.now()));
        listaPartidas.add(new Partida(2, "Yahtzee", false, "Pepe", LocalDateTime.now()));
    }

    // Lombok no quiere trabajar
    public void setListaPartidas(List<Partida> partidas) {
        this.listaPartidas.clear();
        this.listaPartidas.addAll(partidas);
    }
}
