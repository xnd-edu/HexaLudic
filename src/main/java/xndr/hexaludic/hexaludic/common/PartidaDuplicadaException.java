package xndr.hexaludic.hexaludic.common;

public class PartidaDuplicadaException extends RuntimeException {
    public PartidaDuplicadaException() {
        super("Ya existe una partida con ese ID");
    }

    public PartidaDuplicadaException(int id) {
        super("Ya existe una partida con el ID: " + id);
    }
}
