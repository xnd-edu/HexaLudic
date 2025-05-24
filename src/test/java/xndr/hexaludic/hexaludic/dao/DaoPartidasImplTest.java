package xndr.hexaludic.hexaludic.dao;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xndr.hexaludic.hexaludic.common.GuardadoNoEncontradoException;
import xndr.hexaludic.hexaludic.domain.Partida;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Log4j2
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DaoPartidasImplTest {
    @InjectMocks DaoPartidasImpl dao;

    @Mock Partidas database;

    @Test
    @Order(1)
    void getListaPartidas() {
        List<Partida> partidas = new ArrayList<>();
        partidas.add(new Partida(1, "Oca", true, "Arthur", LocalDateTime.now()));
        partidas.add(new Partida(2, "Yahtzee", false, "Pepe", LocalDateTime.now()));
        partidas.add(new Partida(3, "Oca", true, "Alguien", LocalDateTime.now()));

        when(database.getListaPartidas()).thenReturn(partidas);

        List<Partida> resultado = dao.getListaPartidas();

        assertAll(
                () -> assertThat(resultado).isEqualTo(partidas),
                () -> {
                    assert resultado != null;
                    assertThat(resultado.size()).isEqualTo(3);
                }
        );
        log.info("Test getListaPartidas ejecutado");
    }

    @Test
    @Order(2)
    void getPartida() {
        Partida partida = new Partida(1, "Oca", true, "Arthur", LocalDateTime.now());
        when(database.getListaPartidas()).thenReturn(List.of(partida));
        Boolean resultado = dao.getPartida(partida);
        assertThat(resultado).isTrue();
        log.info("Test getPartida ejecutado");
    }

    @Test
    @Order(3)
    void getPartidaById() {
        List<Partida> partidas = new ArrayList<>();
        partidas.add(new Partida(1, "Oca", true, "Arthur", LocalDateTime.now()));
        partidas.add(new Partida(2, "Yahtzee", false, "Pepe", LocalDateTime.now()));
        partidas.add(new Partida(3, "Oca", true, "Alguien", LocalDateTime.now()));
        partidas.add(new Partida(4, "Serpientes y Escaleras", false, "Carlos", LocalDateTime.now()));
        partidas.add(new Partida(5, "Serpientes y Escaleras", true, "Elena", LocalDateTime.now()));
        partidas.add(new Partida(6, "Oca", false, "Marcos", LocalDateTime.now()));
        when(database.getListaPartidas()).thenReturn(partidas);

        boolean resultado = dao.getPartidaById(1);

        assertThat(resultado).isTrue();
        log.info("Test getPartidaById ejecutado");
    }

    @Test
    @Order(4)
    void setPartidas() {
        List<Partida> partidas = new ArrayList<>();
        partidas.add(new Partida(1, "Oca", true, "Arthur", LocalDateTime.now()));
        partidas.add(new Partida(2, "Yahtzee", false, "Pepe", LocalDateTime.now()));
        partidas.add(new Partida(3, "Oca", true, "Alguien", LocalDateTime.now()));
        partidas.add(new Partida(4, "Serpientes y Escaleras", false, "Carlos", LocalDateTime.now()));
        partidas.add(new Partida(5, "Serpientes y Escaleras", true, "Elena", LocalDateTime.now()));
        partidas.add(new Partida(6, "Oca", false, "Marcos", LocalDateTime.now()));
        partidas.add(new Partida(7, "Yahtzee", true, "Pedro", LocalDateTime.now()));

        dao.setPartidas(partidas);

        verify(database, times(1)).setListaPartidas(partidas);
        verifyNoMoreInteractions(database);
        log.info("Test setPartidas ejecutado");
    }

    @Nested
    @DisplayName("Probar carga guardados")
    @Order(5)
    class CargarGuardadoTests {
        @Test
        @DisplayName("Lanza GuardadoNoEncontradoException si el archivo no existe")
        void cargarGuardadoInexistente() {
            String jugador = "JugadorNULL";

            assertThrows(GuardadoNoEncontradoException.class, () -> {
                dao.cargarGuardado(jugador);
            });
            log.info("Test cargarGuardadoInexistente ejecutado");
        }

        @Test
        @DisplayName("Carga correctamente el archivo de guardado")
        void cargarGuardadoValido() {
            String jugador = "TestJunit"; // Verificar que el archivo existe!

            assertThatCode(() -> dao.cargarGuardado(jugador))
                    .doesNotThrowAnyException();

            log.info("Test cargarGuardadoValido ejecutado");
        }
    }
}