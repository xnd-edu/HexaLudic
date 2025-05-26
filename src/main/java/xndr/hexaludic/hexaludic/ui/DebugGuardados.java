package xndr.hexaludic.hexaludic.ui;

import xndr.hexaludic.hexaludic.dao.Guardados;
import xndr.hexaludic.hexaludic.dao.Partidas;
import xndr.hexaludic.hexaludic.dao.TableroOca;

import java.util.Scanner;

public class DebugGuardados {
    public static void main(String[] args) {
        Partidas partidas = new Partidas();
        TableroOca tableroOca = new TableroOca();
        Guardados guardados = new Guardados();

        Scanner sc = new Scanner(System.in);
        System.out.println("Elige una opción:");
        System.out.println("1. Guardar archivo jugador ejemplo");
        System.out.println("2. Leer partida ejemplo");
        System.out.println("3. Guardar tablero OCA ejemplo");
        System.out.println("4. Leer tablero OCA ejemplo");

        switch (sc.nextInt()) {
            case 1:
                System.out.println("Creando partidas de ejemplo...");
                guardados.savePartidas(partidas.getListaPartidas(), "Ejemplo");
                break;
            case 2:
                System.out.println("Leyendo la partida ejemplo...");
                Partidas partidas2 = new Partidas(guardados.loadPartidas("Ejemplo"));
                System.out.println(partidas2.getListaPartidas());
                break;
            case 3:
                System.out.println("Creando tablero de la Oca...");
                guardados.saveTableroOca(tableroOca.getTablero(), "tableroOca.dat");
                break;
            case 4:
                System.out.println("Leyendo el tablero de la Oca...");
                TableroOca tableroOca2 = new TableroOca(guardados.loadTableroOca("tableroOca.dat"));
                System.out.println(tableroOca2.getTablero().toString());
        }

    }
}