package xndr.hexaludic.hexaludic.common;

public class CasillaDuplicadaException extends RuntimeException {
    public CasillaDuplicadaException() {
        super("Ya existe una casilla con ese número");
    }

    public CasillaDuplicadaException(int num) {
        super("Ya existe la casilla número " + num);
    }
}
