package xndr.hexaludic.hexaludic.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Casilla implements Serializable {
    private int num;
    private String tipo;
    private Integer saltoA;
    private int turnosCastigo;

    public Casilla() {
        this.num = 0;
        this.tipo = "";
        this.saltoA = null;
        this.turnosCastigo = 0;
    }

    public Casilla(int num, String tipo) {
        this.num = num;
        this.tipo = tipo;
        this.saltoA = null;
        this.turnosCastigo = 0;
    }

    public Casilla(int num, String tipo, Integer saltoA) {
        this.num = num;
        this.tipo = tipo;
        this.saltoA = saltoA;
        this.turnosCastigo = 0;
    }

    public Casilla(int num, String tipo, int turnosCastigo) {
        this.num = num;
        this.tipo = tipo;
        this.saltoA = null;
        this.turnosCastigo = turnosCastigo;
    }
}
