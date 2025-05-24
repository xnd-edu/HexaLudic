package xndr.hexaludic.hexaludic.service;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xndr.hexaludic.hexaludic.common.PartidaDuplicadaException;
import xndr.hexaludic.hexaludic.dao.DaoPartidas;
import xndr.hexaludic.hexaludic.domain.Partida;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Log4j2
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class ServicioPartidasImplTest {
    private static final Logger logger = LogManager.getLogger(ServicioPartidasImplTest.class);


    @InjectMocks
    ServicioPartidasImpl servicio;

    @Mock
    DaoPartidas dao;

    @Test
    @Order(1)
    void getListaPartidas() {
        List<Partida> partidas = new ArrayList<>();
        partidas.add(new Partida(1, "Oca", true, "Arthur", LocalDateTime.now()));
        partidas.add(new Partida(2, "Yahtzee", false, "Pepe", LocalDateTime.now()));
        partidas.add(new Partida(3, "Oca", true, "Alguien", LocalDateTime.now()));
        partidas.add(new Partida(4, "Serpientes y Escaleras", false, "Carlos", LocalDateTime.now()));
        partidas.add(new Partida(5, "Serpientes y Escaleras", true, "Elena", LocalDateTime.now()));
        partidas.add(new Partida(6, "Oca", false, "Marcos", LocalDateTime.now()));
        partidas.add(new Partida(7, "Yahtzee", true, "Pedro", LocalDateTime.now()));

        when(dao.getListaPartidas()).thenReturn(partidas);
        List<Partida> resultado = servicio.getListaPartidas();

        assertThat(resultado).isEqualTo(partidas);
        log.info("Test getListaPartidas ejecutado");
    }

    @Nested
    @DisplayName("Tests para añadir partidas")
    @Order(4)
    class AddPartidaTests {

        @Test
        void addPartidaValida() {
            Partida partida = new Partida(8, "Yahtzee", true, "Pepe", LocalDateTime.now());
            when(dao.addPartida(partida)).thenReturn(true);

            boolean resultado = servicio.addPartida(partida);

            assertThat(resultado).isTrue();
            log.info("Test addPartidaValida ejecutado");
        }

        @Test
        void addPartidaInvalida() {
            Partida partidaDuplicada = new Partida(1, "Oca", true, "Arthur", LocalDateTime.now());

            when(dao.addPartida(partidaDuplicada))
                    .thenThrow(new PartidaDuplicadaException(partidaDuplicada.getId()));

            assertThrows(PartidaDuplicadaException.class, () -> {
                servicio.addPartida(partidaDuplicada);
            });

            log.info("Test addPartidaDuplicadaLanzaExcepcion ejecutado");
        }
    }

    @Test
    @Order(3)
    void removePartida() {
        Partida partida = new Partida(1, "Oca", true, "Arthur", LocalDateTime.now());
        servicio.removePartida(partida);
        verify(dao, times(1)).removePartida(partida);
        verifyNoMoreInteractions(dao);
        log.info("Test removePartida ejecutado");
    }

    @Test
    @Order(2)
    void setPartidas() {
        List<Partida> partidas = new ArrayList<>();
        partidas.add(new Partida(1, "Oca", true, "Arthur", LocalDateTime.now()));
        partidas.add(new Partida(2, "Yahtzee", false, "Pepe", LocalDateTime.now()));
        partidas.add(new Partida(3, "Oca", true, "Alguien", LocalDateTime.now()));
        partidas.add(new Partida(4, "Serpientes y Escaleras", false, "Carlos", LocalDateTime.now()));
        partidas.add(new Partida(5, "Serpientes y Escaleras", true, "Elena", LocalDateTime.now()));
        partidas.add(new Partida(6, "Oca", false, "Marcos", LocalDateTime.now()));
        partidas.add(new Partida(7, "Yahtzee", true, "Pedro", LocalDateTime.now()));

        servicio.setPartidas(partidas);
        verify(dao, times(1)).setPartidas(partidas);
        verifyNoMoreInteractions(dao);
        log.info("Test setPartidas ejecutado");
    }

    @Test
    @Order(5)
    void getPartida() {
        Partida partida = new Partida(20, "Oca", true, "Pepe", LocalDateTime.now());

        when(dao.getPartida(partida)).thenReturn(true);

        Boolean resultado = servicio.getPartida(partida);

        assertThat(resultado).isEqualTo(true);
        log.info("Test getPartida ejecutado");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 5, 7})
    @Order(6)
    void getPartidaById(int id) {
        Partida partida1 = new Partida(1, "Oca", true, "Arthur", LocalDateTime.now());
        Partida partida2 = new Partida(2, "Yahtzee", false, "Pepe", LocalDateTime.now());
        Partida partida3 = (new Partida(3, "Oca", true, "Alguien", LocalDateTime.now()));
        Partida partida4 = new Partida(5, "Serpientes y Escaleras", true, "Elena", LocalDateTime.now());
        Partida partida5 = new Partida(7, "Yahtzee", true, "Pedro", LocalDateTime.now());

        when(dao.getPartidaById(id)).thenReturn(true);

        boolean resultado = servicio.getPartidaById(id);

        assertTrue(resultado);
        log.info("Test getPartidaById ejecutado");
    }
}
