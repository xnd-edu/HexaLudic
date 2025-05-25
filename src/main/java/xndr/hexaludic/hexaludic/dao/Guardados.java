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

        File file = new File(new Config().loadPathProperties() + jugador + ".json");
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
}
