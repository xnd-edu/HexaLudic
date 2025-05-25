package xndr.hexaludic.hexaludic.service;

import xndr.hexaludic.hexaludic.dao.DaoTableroOca;
import xndr.hexaludic.hexaludic.dao.DaoTableroOcaImpl;
import xndr.hexaludic.hexaludic.domain.Casilla;

import java.util.List;

public class ServicioTableroOcaImpl implements ServicioTableroOca {
    private final DaoTableroOca daoTableroOca;

    public ServicioTableroOcaImpl() {
        this.daoTableroOca = new DaoTableroOcaImpl();
    }

    public ServicioTableroOcaImpl(DaoTableroOca daoTableroOca) {
        this.daoTableroOca = daoTableroOca;
    }

    @Override
    public List<Casilla> getListaCasillas() {
        return daoTableroOca.getListaCasillas();
    }

    @Override
    public boolean removeCasilla(Casilla casilla) {
        if (daoTableroOca.getCasilla(casilla))
            daoTableroOca.removeCasilla(casilla);
        return false;
    }

    @Override
    public boolean addCasilla(Casilla casilla) {
        return daoTableroOca.addCasilla(casilla);
    }

    @Override
    public void setCasillas(List<Casilla> casillas) {
        daoTableroOca.setCasillas(casillas);
    }

    @Override
    public boolean getCasilla(Casilla casilla) {
        return daoTableroOca.getCasilla(casilla);
    }

    @Override
    public boolean getCasillaByNum(int num) {
        return daoTableroOca.getCasillaByNum(num);
    }

    @Override
    public boolean updateCasilla(Casilla casilla1, Casilla casilla2) {
        if (daoTableroOca.getCasilla(casilla2))
            return daoTableroOca.updateCasilla(casilla1, casilla2);
        else return false;
    }

    @Override
    public void cargarTablero(String nombreArchivo) {
        daoTableroOca.cargarTablero(nombreArchivo);
    }

    @Override
    public void guardarTablero(String nombreArchivo) {
        daoTableroOca.guardarTablero(nombreArchivo);
    }
}
