package org.lc.v1.game.modals;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.lc.v1.database.Query;
import org.lc.v1.UITools;

public class SettingsModal extends StackPane {
    private final StackPane fondoOscuro;
    private final VBox contenido;
    private Runnable alVolverAlMenu;
    private boolean animando = false;
    private boolean visible = false;

    private static final double ANCHO_MODAL = 600;
    private static final double ALTO_MODAL = 400;
    private static final double ANCHO_PANTALLA = 1280;
    private static final double ALTO_PANTALLA = 720;

    public SettingsModal() {
        setPickOnBounds(false);
        setVisible(false);
        setMouseTransparent(true);

        fondoOscuro = UITools.crearOverlay(ANCHO_PANTALLA, ALTO_PANTALLA, 0.7);

        contenido = new VBox(20);
        contenido.setAlignment(Pos.CENTER);
        UITools.setTamanoFijo(contenido, ANCHO_MODAL, ALTO_MODAL);
        contenido.setPickOnBounds(true);
        contenido.setStyle(
                "-fx-background-image: url('/images/modals/SettingsMapModal.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 0;"
        );

        getChildren().addAll(fondoOscuro, contenido);
        setAlignment(Pos.CENTER);
        contenido.setOnMouseClicked(Event::consume);
    }

    public void mostrar(Runnable alVolverAlMenu) {
        if (animando || visible) return;
        animando = true;
        visible = true;
        this.alVolverAlMenu = alVolverAlMenu;

        contenido.getChildren().clear();
        construirContenido();

        setVisible(true);
        setMouseTransparent(false);
        toFront();

        UITools.mostrarAnimacion(contenido, () -> animando = false);
    }

    private void construirContenido() {
        Button botonBasura = UITools.imagenBoton("/images/buttons/TrashButton.png", 120, 120);
        botonBasura.setTranslateX(-83);
        botonBasura.setTranslateY(148);
        botonBasura.setOnAction(e -> mostrarConfirmacionBorrar());

        Button botonVolverMenu = UITools.imagenBoton("/images/buttons/HomeButton.png", 120, 120);
        botonVolverMenu.setTranslateX(80);
        botonVolverMenu.setTranslateY(10);
        botonVolverMenu.setOnAction(e -> {
            ocultar();
            if (alVolverAlMenu != null) alVolverAlMenu.run();
        });

        Button botonSalir = UITools.imagenBoton("/images/buttons/ExitButton.png", 300, 300);
        botonSalir.setTranslateX(0);
        botonSalir.setTranslateY(-10);
        botonSalir.setOnAction(e -> Platform.exit());

        Button botonCerrar = UITools.imagenBoton("/images/buttons/CloseButton.png", 80, 80);
        botonCerrar.setTranslateX(250);
        botonCerrar.setTranslateY(-375);
        botonCerrar.setOnAction(e -> ocultar());

        contenido.getChildren().addAll(botonBasura, botonVolverMenu, botonSalir, botonCerrar);
    }

    private void mostrarConfirmacionBorrar() {
        StackPane modalConfirmacion = new StackPane();
        modalConfirmacion.setPickOnBounds(false);
        modalConfirmacion.setAlignment(Pos.CENTER);

        StackPane overlayConfirmacion = UITools.crearOverlay(ANCHO_PANTALLA, ALTO_PANTALLA, 0.8);

        VBox contenidoConfirmacion = new VBox(20);
        contenidoConfirmacion.setAlignment(Pos.CENTER);
        UITools.setTamanoFijo(contenidoConfirmacion, 500, 350);
        contenidoConfirmacion.setStyle(
                "-fx-background-image: url('/images/modals/DeleteProgressModal.png');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 0;"
        );

        Button botonCancelar = UITools.imagenBoton("/images/buttons/CancelButton.png", 200, 90);
        botonCancelar.setTranslateX(-110);
        botonCancelar.setTranslateY(130);
        botonCancelar.setOnAction(e -> getChildren().remove(modalConfirmacion));

        Button botonEliminar = UITools.imagenBoton("/images/buttons/DeleteAllButton.png", 200, 90);
        botonEliminar.setTranslateX(120);
        botonEliminar.setTranslateY(16);
        botonEliminar.setOnAction(e -> {
            Query.resetProgress();
            getChildren().remove(modalConfirmacion);
            ocultar();
            if (alVolverAlMenu != null) alVolverAlMenu.run();
        });

        contenidoConfirmacion.getChildren().addAll(botonCancelar, botonEliminar);
        modalConfirmacion.getChildren().addAll(overlayConfirmacion, contenidoConfirmacion);
        UITools.mostrarAnimacion(modalConfirmacion, null);
        getChildren().add(modalConfirmacion);
    }

    public void ocultar() {
        if (animando || !visible) return;
        animando = true;
        visible = false;
        UITools.ocultarModal(this, contenido, () -> animando = false);
    }
}