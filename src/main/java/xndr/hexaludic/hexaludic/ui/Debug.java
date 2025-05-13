package xndr.hexaludic.hexaludic.ui;

import xndr.hexaludic.hexaludic.dao.Guardados;
import xndr.hexaludic.hexaludic.dao.Partidas;

import java.util.Scanner;

public class Debug {
    public static void main(String[] args) {
        Partidas partidas = new Partidas();
        Guardados guardados = new Guardados();

        Scanner sc = new Scanner(System.in);
        System.out.println("Elige una opción:");
        System.out.println("1. Guardar partida ejemplo");
        System.out.println("2. Leer partida ejemplo");

        switch (sc.nextInt()) {
            case 1:
                System.out.println("Creando partida de ejemplo...");
                guardados.savePartidas(partidas.getListaPartidas(), "Ejemplo");
                break;
            case 2:
                System.out.println("Leyendo la partida ejemplo...");
                Partidas partidas2 = new Partidas(guardados.loadPartidas("Ejemplo"));
                System.out.println(partidas2.getListaPartidas());
        }

    }
}