package org.lc.v1.screens;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.lc.v1.App;
import org.lc.v1.database.Query;
import org.lc.v1.UITools;
import org.lc.v1.game.levels.*;
import org.lc.v1.game.modals.LevelInfoModal;
import org.lc.v1.game.modals.SettingsModal;
import org.lc.v1.game.player.PlayerMarker;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final Pane raiz;
    private final App aplicacion;

    private static final Point2D[] POSICIONES_NIVEL = {
            new Point2D(315, 475),
            new Point2D(560, 525),
            new Point2D(805, 485),
            new Point2D(664, 370),
            new Point2D(630, 272)
    };

    private static final Point2D[] POSICIONES_JUGADOR = {
            new Point2D(210, 420),
            new Point2D(450, 490),
            new Point2D(705, 450),
            new Point2D(564, 325),
            new Point2D(545, 227),
    };

    private final List<LevelNode> nodosNivel = new ArrayList<>();
    private PlayerMarker marcadorJugador;
    private LevelInfoModal modalInfoNivel;
    private int nivelActual;

    public Map(App app) {
        this.aplicacion = app;
        this.raiz = new Pane();
        this.nivelActual = calcularNivelActual();
        construirUI();
    }

    private void construirUI() {
        raiz.setPrefSize(1280, 720);

        ImageView fondo = UITools.fondo("/images/bgMap01.png", 1280, 720);
        raiz.getChildren().add(fondo);

        for (int i = 1; i <= 5; i++) {
            LevelState estado = obtenerEstadoNivel(i);
            int estrellas = Query.getStars(i);
            LevelData datos = new LevelData(i, estado, estrellas);

            LevelNode nodo = new LevelNode(datos);
            UITools.setPosicion(nodo, POSICIONES_NIVEL[i-1].getX(), POSICIONES_NIVEL[i-1].getY());
            nodo.alSeleccionar(() -> alHacerClicEnNivel(nodo));

            raiz.getChildren().add(nodo);
            nodosNivel.add(nodo);
        }

        marcadorJugador = new PlayerMarker();
        marcadorJugador.setPosicion(POSICIONES_JUGADOR[nivelActual - 1]);
        raiz.getChildren().add(marcadorJugador.getVista());

        modalInfoNivel = new LevelInfoModal();
        modalInfoNivel.setVisible(false);
        raiz.getChildren().add(modalInfoNivel);

        Button botonConfiguracion = UITools.imagenBoton("/images/buttons/configButton.png", 80, 80);
        UITools.setPosicion(botonConfiguracion, 1160, 20);
        botonConfiguracion.setOnAction(e -> {
            SettingsModal modal = new SettingsModal();
            modal.mostrar(() -> {
                refrescarMapa();
                aplicacion.mostrarMainMenu();
            });
            raiz.getChildren().add(modal);
        });
        raiz.getChildren().add(botonConfiguracion);
    }

    private void alHacerClicEnNivel(LevelNode nodo) {
        LevelData datos = nodo.getData();
        if (!datos.esAccesible()) return;

        marcadorJugador.moverA(POSICIONES_JUGADOR[datos.getNivelNumero() - 1], null);
        nivelActual = datos.getNivelNumero();

        LevelInfoType tipo = nodo.getModalTipo();
        if (tipo == null) return;

        modalInfoNivel.mostrar(
                datos, tipo,
                () -> { modalInfoNivel.ocultar(); aplicacion.mostrarLevel(datos.getNivelNumero()); },
                () -> { modalInfoNivel.ocultar(); aplicacion.mostrarLevel(datos.getNivelNumero()); },
                () -> modalInfoNivel.ocultar()
        );
    }

    private LevelState obtenerEstadoNivel(int nivel) {
        int ultimoDesbloqueado = Query.getLastUnlockedLevel();
        boolean completado = Query.isCompleted(nivel);
        int estrellas = Query.getStars(nivel);

        if (completado && estrellas > 0) return LevelState.COMPLETADO;
        if (completado) return LevelState.DESBLOQUEADO;
        if (nivel == ultimoDesbloqueado) return LevelState.ACTUAL;
        if (nivel < ultimoDesbloqueado) return LevelState.DESBLOQUEADO;
        return LevelState.BLOQUEADO;
    }

    private int calcularNivelActual() {
        return Query.getLastUnlockedLevel();
    }

    private void refrescarMapa() {
        for (LevelNode nodo : nodosNivel) {
            LevelData datos = nodo.getData();
            LevelState nuevoEstado = obtenerEstadoNivel(datos.getNivelNumero());
            int nuevasEstrellas = Query.getStars(datos.getNivelNumero());
            datos.setEstado(nuevoEstado);
            datos.setEstrellasObtenidas(nuevasEstrellas);
            nodo.refrescar();
        }

        nivelActual = calcularNivelActual();
        marcadorJugador.setPosicion(POSICIONES_JUGADOR[nivelActual - 1]);
    }

    public Pane getVista() {
        return raiz;
    }
}