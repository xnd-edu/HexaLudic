package xndr.hexaludic.hexaludic.service;

import xndr.hexaludic.hexaludic.domain.Casilla;

import java.util.List;

public interface ServicioTableroOca {
    public List<Casilla> getListaCasillas();
    boolean removeCasilla(Casilla casilla);
    boolean addCasilla(Casilla casilla);
    void setCasillas(List<Casilla> casillas);
    boolean getCasilla(Casilla casilla);
    boolean getCasillaByNum(int num);
    boolean updateCasilla(Casilla casilla1, Casilla casilla2);
    void cargarTablero(String nombreArchivo);
    void guardarTablero(String nombreArchivo);
}
