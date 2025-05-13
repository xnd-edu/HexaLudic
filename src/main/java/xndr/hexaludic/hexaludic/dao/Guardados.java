package xndr.hexaludic.hexaludic.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import xndr.hexaludic.hexaludic.common.Config;
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
        Gson gson = GsonFactory.create();
        Type userListType = new TypeToken<ArrayList<Partida>>() {}.getType();
        log.info("Cargando partidas");
        List<Partida> Partidas = null;
        try {
            Partidas = gson.fromJson(
                    new FileReader(new Config().loadPathProperties() + jugador + ".json"),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return Partidas;
    }

    public boolean savePartidas(List<Partida> partidas, String jugador) {
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
}
