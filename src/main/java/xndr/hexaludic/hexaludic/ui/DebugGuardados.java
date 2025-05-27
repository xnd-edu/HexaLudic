package xndr.hexaludic.hexaludic.ui;

import xndr.hexaludic.hexaludic.dao.DaoPartidasImpl;
import xndr.hexaludic.hexaludic.dao.Guardados;
import xndr.hexaludic.hexaludic.dao.Partidas;
import xndr.hexaludic.hexaludic.dao.TableroOca;
import xndr.hexaludic.hexaludic.service.ServicioPartidas;
import xndr.hexaludic.hexaludic.service.ServicioPartidasImpl;

import java.util.Scanner;


/* Clase para probar la lectura/escritura de ficheros */
public class DebugGuardados {
    public static void main(String[] args) {
        Partidas partidas = new Partidas();
        TableroOca tableroOca = new TableroOca();
        Guardados guardados = new Guardados();
        ServicioPartidasImpl servicioPartidas = new ServicioPartidasImpl(new DaoPartidasImpl());

        Scanner sc = new Scanner(System.in);
        System.out.println("Elige una opción:");
        System.out.println("1. Guardar archivo jugador ejemplo (JSON)");
        System.out.println("2. Leer partida ejemplo (JSON)");
        System.out.println("3. Guardar archivo jugador ejemplo (Txt)");
        System.out.println("4. Leer partida ejemplo (Txt)");
        System.out.println("5. Guardar tablero OCA ejemplo");
        System.out.println("6. Leer tablero OCA ejemplo");

        switch (sc.nextInt()) {
            case 1:
                System.out.println("Creando partidas de ejemplo...");
                servicioPartidas.guardarGuardado("Ejemplo");
                break;
            case 2:
                System.out.println("Leyendo la partida ejemplo...");
                servicioPartidas.cargarGuardado("Ejemplo");
                System.out.println(servicioPartidas.getListaPartidas().toString());
                break;
            case 3:
                System.out.println("Creando partidas de ejemplo...");
                servicioPartidas.guardarGuardadoTxt("Ejemplo");
            case 4:
                System.out.println("Leyendo la partida ejemplo...");
                servicioPartidas.cargarGuardadotxt("Ejemplo");
                System.out.println(servicioPartidas.getListaPartidas().toString());
            case 5:
                System.out.println("Creando tablero de la Oca...");
                guardados.saveTableroOca(tableroOca.getTablero(), "tableroOca.dat");
                break;
            case 6:
                System.out.println("Leyendo el tablero de la Oca...");
                TableroOca tableroOca2 = new TableroOca(guardados.loadTableroOca("tableroOca.dat"));
                System.out.println(tableroOca2.getTablero().toString());
        }

    }
}