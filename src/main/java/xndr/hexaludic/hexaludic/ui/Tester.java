package xndr.hexaludic.hexaludic.ui;

import xndr.hexaludic.hexaludic.dao.DaoPartidasImpl;
import xndr.hexaludic.hexaludic.domain.Partida;
import xndr.hexaludic.hexaludic.service.ServicioPartidasImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Tester {
    private static final ServicioPartidasImpl servicioPartidas = new ServicioPartidasImpl(new DaoPartidasImpl());
    // Los ids de las partidas son el mismo para ambos jugadores.
    // Es decir, cuando dos jugadores juegan un juego ambos están jugando la partida "10", o "11", o "20",
    // para evitar un conflicto de IDs entre los guardados.
    private static final int idPartida = 100;
    private static final String juego = "Oca";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-------------------" + "HexaLudic" + "-------------------");
        System.out.println("\nBienvenido a las pruebas de HexaLudic.");
        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Jugar una partida");
        System.out.println("2. Ver el top de jugadores");
        System.out.println("3. Salir");

        switch (sc.nextInt()) {
            case 1:
                sc.nextLine();
                System.out.println("En esta prueba se simulara una partida del juego de la Oca");
                System.out.println("para poner a prueba el sistema de guardados de los jugadores.");
                System.out.println();
                System.out.println("Jugador 1, ingrese su nombre: ");
                String jugador1 = sc.nextLine();
                System.out.println("Jugador 2, ingrese su nombre: ");
                String jugador2 = sc.nextLine();

                ejecutarPartida(jugador1, jugador2);
                break;
            case 2:
                mostrarTop();
                break;
            case 3:
                System.out.println("Saliendo...");
        }

    }

    private static void ejecutarPartida(String jugador1, String jugador2) {
        Random rnd = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.println("Simulando partida...");

        System.out.println();

        boolean resultado = rnd.nextBoolean();
        System.out.println("Ha ganado: ¡" + (resultado ? jugador1 : jugador2) + "!");

        System.out.println("\nPulse Enter para continuar...");
        sc.nextLine();

        System.out.println("Guardando partida en el archivo de ambos jugadores...");
        servicioPartidas.cargarGuardado(jugador1);
        System.out.println("Jugador 1 cargado: " + jugador1);
        System.out.println("Guardando partida...");
        servicioPartidas.addPartida(new Partida(idPartida, juego, resultado, jugador2, LocalDateTime.now()));
        servicioPartidas.guardarGuardado(jugador1);
        System.out.println("Partida guardada: " + jugador1);

        servicioPartidas.cargarGuardado(jugador2);
        System.out.println("Jugador 2 cargado: " + jugador2);
        System.out.println("Guardando partida...");
        servicioPartidas.addPartida(new Partida(idPartida, juego, !resultado, jugador1, LocalDateTime.now()));
        servicioPartidas.guardarGuardado(jugador2);
        System.out.println("Partida guardada: " + jugador2);

        System.out.println("\nFin de la prueba.");
    }

    private static void mostrarTop() {

    }
}
