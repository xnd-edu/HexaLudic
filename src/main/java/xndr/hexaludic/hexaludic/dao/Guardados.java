package xndr.hexaludic.hexaludic.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import xndr.hexaludic.hexaludic.common.Config;
import xndr.hexaludic.hexaludic.domain.Casilla;
import xndr.hexaludic.hexaludic.domain.GsonFactory;
import xndr.hexaludic.hexaludic.domain.Partida;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Guardados {

    public List<Partida> loadPartidas(String jugador) {
        String path = new Config().loadPathProperties() + jugador + ".json";
        log.info("Leyendo guardado de " + jugador + ": " + path);
        Gson gson = GsonFactory.create();
        Type userListType = new TypeToken<ArrayList<Partida>>() {}.getType();
        List<Partida> partidas = new ArrayList<>();

        File file = new File(path);
        // Si el jugador es nuevo, se le crea un archivo nuevo
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(partidas, writer);
                }
                log.info("Archivo creado para jugador nuevo: " + jugador);
            } catch (IOException e) {
                log.error("Error creando archivo nuevo para " + jugador, e);
                return partidas;
            }
        }

        // Si ya existe, lo leemos
        try (FileReader reader = new FileReader(file)) {
            partidas = gson.fromJson(reader, userListType);
            if (partidas == null) partidas = new ArrayList<>();
        } catch (IOException e) {
            log.error("Error leyendo archivo de partidas de " + jugador, e);
        }

        log.info("Partidas cargadas: " + partidas.size());
        return partidas;
    }


    public boolean savePartidas(List<Partida> partidas, String jugador) {
        String path = new Config().loadPathProperties() + jugador + ".json";
        Gson gson = GsonFactory.create();
        System.out.println(gson.toJson(partidas));

        File file = new File(path);
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file)) {
            gson.toJson(partidas, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public List<Casilla> loadTableroOca(String nombreArchivo) {
        String path = new Config().loadGamePathProperties() + nombreArchivo;
        File file = new File(path);
        log.info("Leyendo tablero Oca... " + path);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            log.info("Tablero Oca cargado.");
            return (List<Casilla>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public boolean saveTableroOca(List<Casilla> tableroOca, String nombreArchivo) {
        String path = new Config().loadGamePathProperties() + nombreArchivo;
        File file = new File(path);
        file.getParentFile().mkdirs();
        log.info("Guardando tablero Oca: " + tableroOca.toString() + " en " + path + "...");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(tableroOca);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public List<Partida> loadPartidasTxt(String jugador) {
        String path = new Config().loadPathProperties() + jugador + ".txt";
        log.info("Leyendo guardado de " + jugador + ": " + path);
        List<Partida> partidas = new ArrayList<>();

        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Formato esperado: id,juego,victoria,jugador2,fechaPartida
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    int id = Integer.parseInt(partes[0]);
                    String juego = partes[1];
                    boolean victoria = Boolean.parseBoolean(partes[2]);
                    String jugador2 = partes[3];
                    LocalDateTime fecha = LocalDateTime.parse(partes[4]);

                    partidas.add(new Partida(id, juego, victoria, jugador2, fecha));
                } else {
                    log.warn("Línea mal esrcita: " + linea);
                }
            }
        } catch (IOException e) {
            log.error("Error leyendo archivo de partidas de " + jugador, e);
        }

        log.info("Partidas cargadas de fichero de texto: " + partidas.size());
        return partidas;
    }

    public boolean savePartidasTxt(List<Partida> listaPartidas, String jugador) {
        String path = new Config().loadPathProperties() + jugador + ".txt";
        File file = new File(path);
        file.getParentFile().mkdirs();
        log.info("Guardando partidas en " + path + "...");

        try (FileWriter fw = new FileWriter(file)) {
            for (Partida partida : listaPartidas) {
                fw.write(partida.toStringTxt());
            }
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
