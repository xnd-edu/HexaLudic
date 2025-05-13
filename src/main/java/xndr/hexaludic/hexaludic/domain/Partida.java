package xndr.hexaludic.hexaludic.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Partida {
    private int id;
    private LocalDateTime fechaPartida;
    private boolean victoria;
    private String jugador2;


    public Partida(int id, boolean victoria, String jugador2, LocalDateTime fechaPartida) {
        this.id = id;
        this.victoria = victoria;
        this.jugador2 = jugador2;
        this.fechaPartida = fechaPartida;
    }
}
