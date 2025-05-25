package xndr.hexaludic.hexaludic.dao;

import xndr.hexaludic.hexaludic.common.CasillaDuplicadaException;
import xndr.hexaludic.hexaludic.common.GuardadoNoEncontradoException;
import xndr.hexaludic.hexaludic.domain.Casilla;

import java.util.List;

public class DaoTableroOcaImpl implements DaoTableroOca {
    private final TableroOca tableroOca;

    public DaoTableroOcaImpl() {
        this.tableroOca = new TableroOca();
    }

    public DaoTableroOcaImpl(TableroOca tableroOca) {
        this.tableroOca = tableroOca;
    }

    @Override
    public List<Casilla> getListaCasillas() {
        return tableroOca.getTablero();
    }

    @Override
    public void removeCasilla(Casilla casilla) {
        tableroOca.getTablero().remove(casilla);
    }

    @Override
    public boolean addCasilla(Casilla casilla) {
        boolean existe = getCasilla(casilla);
        if (existe) {
            throw new CasillaDuplicadaException(casilla.getNum());
        }
        return tableroOca.getTablero().add(casilla);
    }

    @Override
    public boolean getCasilla(Casilla casilla) {
        List<Casilla> tableroOca = getListaCasillas();
        return tableroOca.stream().anyMatch(c -> c.getNum() == casilla.getNum());
    }

    @Override
    public boolean getCasillaByNum(int num) {
        List<Casilla> tableroOca = getListaCasillas();
        return tableroOca.stream().anyMatch(c -> c.getNum() == num);
    }

    @Override
    public boolean updateCasilla(Casilla casilla1, Casilla casilla2) {
        removeCasilla(casilla1);
        addCasilla(casilla2);
        return getCasilla(casilla2);
    }

    @Override
    public void setCasillas(List<Casilla> tableroOca) {
        this.tableroOca.setTablero(tableroOca);
    }

    @Override
    public void cargarTablero(String nombreArchivo) {
        Guardados guardados = new Guardados();
        List<Casilla> tableroOca = guardados.loadTableroOca(nombreArchivo);
        if (tableroOca == null || tableroOca.isEmpty()) {
            throw new GuardadoNoEncontradoException(nombreArchivo);
        }
        setCasillas(tableroOca);
    }

    @Override
    public boolean guardarTablero(String jugador) {
        Guardados guardados = new Guardados();
        return guardados.saveTableroOca(getListaCasillas(), jugador);
    }
}
