package xndr.hexaludic.hexaludic.dao;

import lombok.Data;
import xndr.hexaludic.hexaludic.domain.Casilla;

import java.util.*;

@Data
public class TableroOca {
    private final List<Casilla> tablero;

    public TableroOca(List<Casilla> tablero) {
        this.tablero = tablero;
    }

    public TableroOca() {

        tablero = new ArrayList<>();

        // Casillas tipo oca
        Set<Integer> ocas = Set.of(1, 5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 60);
        for (int oca : ocas) {
            tablero.add(new Casilla(oca, "oca"));
        }

        // Puentes y dados
        tablero.add(new Casilla(6, "puente", 12));
        tablero.add(new Casilla(12, "puente", 6));
        tablero.add(new Casilla(26, "puente", 53));
        tablero.add(new Casilla(53, "puente", 26));

        // Pozo
        tablero.add(new Casilla(31, "pozo", 3));

        // Posada y cárcel
        tablero.add(new Casilla(19, "castigo", 2));
        tablero.add(new Casilla(56, "castigo", 3));

        // Laberinto y muerte
        tablero.add(new Casilla(42, "laberinto"));
        tablero.add(new Casilla(58, "muerte"));

        // Casillas normales
        for (int i = 1; i <= 63; i++) {
            int finalI = i;
            boolean existe = tablero.stream().anyMatch(c -> c.getNum() == finalI);
            if (!existe) {
                tablero.add(new Casilla(i, "normal"));
            }
        }

        // Ordena por número por si acaso
        tablero.sort(Comparator.comparingInt(Casilla::getNum));
    }
}
