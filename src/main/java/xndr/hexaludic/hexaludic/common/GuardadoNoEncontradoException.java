package xndr.hexaludic.hexaludic.common;

public class GuardadoNoEncontradoException extends RuntimeException {
    public GuardadoNoEncontradoException() {
        super("Archivo de guardado no encontrado");
    }

    public GuardadoNoEncontradoException(String jugador) {
        super("No se encontró el archivo de guardado para el jugador: " + jugador);
    }
}

