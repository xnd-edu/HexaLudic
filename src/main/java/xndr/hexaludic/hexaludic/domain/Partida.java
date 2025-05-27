package xndr.hexaludic.hexaludic.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Partida {
    private int id;
    private String juego;
    private LocalDateTime fechaPartida;
    private boolean victoria;
    private String jugador2;


    public Partida(int id, String juego, boolean victoria, String jugador2, LocalDateTime fechaPartida) {
        this.id = id;
        this.juego = juego;
        this.victoria = victoria;
        this.jugador2 = jugador2;
        this.fechaPartida = fechaPartida;
    }

    public String toStringTxt() {
        return id + "," + juego + "," + victoria + "," + jugador2 + "," + fechaPartida + "\n";
    }
}
