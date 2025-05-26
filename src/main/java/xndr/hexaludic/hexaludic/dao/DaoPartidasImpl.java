package xndr.hexaludic.hexaludic.dao;

import lombok.extern.log4j.Log4j2;
import xndr.hexaludic.hexaludic.common.Config;
import xndr.hexaludic.hexaludic.common.PartidaDuplicadaException;
import xndr.hexaludic.hexaludic.domain.Partida;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class DaoPartidasImpl implements DaoPartidas {

    private final Partidas partidas;

    public DaoPartidasImpl() {
        this.partidas = new Partidas();
    }

    public DaoPartidasImpl(Partidas partidas) {
        this.partidas = partidas;
    }

    @Override
    public List<Partida> getListaPartidas() {
        return partidas.getListaPartidas();
    }

    @Override
    public boolean removePartida(Partida partida) {
        return partidas.getListaPartidas().remove(partida);
    }

    @Override
    public boolean addPartida(Partida partida) {
        boolean existe = getPartida(partida);
        if (existe) {
            throw new PartidaDuplicadaException(partida.getId());
        }
        return partidas.getListaPartidas().add(partida);
    }

    @Override
    public boolean getPartida(Partida partida) {
        List<Partida> partidas = getListaPartidas();
        return partidas.stream().anyMatch(p -> p.getId() == partida.getId());
    }

    @Override
    public boolean getPartidaById(int id) {
        List<Partida> partidas = getListaPartidas();
        return partidas.stream().anyMatch(p -> p.getId() == id);
    }

    @Override
    public boolean updatePartida(Partida partida1, Partida partida2) {
        removePartida(partida1);
        partidas.getListaPartidas().add(partida2);
        return getPartida(partida2);
    }

    @Override
    public void setPartidas(List<Partida> partidas) {
        this.partidas.setListaPartidas(partidas);
    }

    @Override
    public List<Partida> cargarGuardado(String jugador) {
        Guardados guardados = new Guardados();
        List<Partida> partidas = guardados.loadPartidas(jugador);
        setPartidas(partidas);
        log.info("Partidas de " + jugador + " cargadas: " + partidas);
        return partidas;
    }

    @Override
    public boolean guardarGuardado(String jugador) {
        Guardados guardados = new Guardados();
        return guardados.savePartidas(getListaPartidas(), jugador);
    }

    @Override
    public Map<String, Integer> getRankingVictorias() {
        Map<String, Integer> ranking = new HashMap<>();
        String path = new Config().loadPathProperties();
        File folder = new File(path);

        if (!folder.exists() || !folder.isDirectory()) {
            log.error("Directorio de guardados no encontrado: " + path);
            return ranking;
        }

        File[] archivos = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (archivos == null) {
            log.error("No se encontraron archivos de guardado en " + path);
            return ranking;
        }

        Guardados guardados = new Guardados();
        for (File archivo : archivos) {
            String nombreJugador = archivo.getName().replace(".json", "");
            List<Partida> partidas = guardados.loadPartidas(nombreJugador);
            if (partidas == null) continue;

            int victorias = (int) partidas.stream()
                    .filter(Partida::isVictoria) // true = ganó el dueño del archivo
                    .count();

            ranking.put(nombreJugador, victorias);
        }

        return ranking.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(
                        LinkedHashMap::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        Map::putAll
                );
    }

}
