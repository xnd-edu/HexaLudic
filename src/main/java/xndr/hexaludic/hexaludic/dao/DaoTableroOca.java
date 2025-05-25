package xndr.hexaludic.hexaludic.dao;

import xndr.hexaludic.hexaludic.domain.Casilla;

import java.util.List;

public interface DaoTableroOca {
    List<Casilla> getListaCasillas();
    void removeCasilla(Casilla casilla);
    boolean addCasilla(Casilla casilla);
    boolean getCasilla(Casilla casilla);
    boolean getCasillaByNum(int num);
    boolean updateCasilla(Casilla casilla1, Casilla casilla2);
    void setCasillas(List<Casilla> casillas);
    void cargarTablero(String nombreArchivo);
    boolean guardarTablero(String nombreArchivo);
}
