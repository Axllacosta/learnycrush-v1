package org.lc.v1.game.modals;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.lc.v1.UITools;
import org.lc.v1.game.levels.LevelData;
import org.lc.v1.game.levels.LevelInfoType;

public class LevelInfoModal extends StackPane {
    private final StackPane fondoOscuro;
    private final VBox contenido;
    private Runnable alJugar;
    private Runnable alReintentar;
    private Runnable alVolver;
    private boolean animando = false;
    private boolean visible = false;

    private static final double ANCHO_MODAL = 800;
    private static final double ALTO_MODAL = 450;
    private static final double ANCHO_PANTALLA = 1280;
    private static final double ALTO_PANTALLA = 720;

    private static final String[] MODALES_NIVELES = {
            "/images/modals/ModalLevel_1.png",
            "/images/modals/ModalLevel_2.png",
            "/images/modals/ModalLevel_3.png",
            "/images/modals/ModalLevel_4.png",
            "/images/modals/ModalLevel_5.png"
    };

    public LevelInfoModal() {
        setPickOnBounds(false);
        setVisible(false);
        setMouseTransparent(true);

        fondoOscuro = UITools.crearOverlay(ANCHO_PANTALLA, ALTO_PANTALLA, 0.7);

        contenido = new VBox(20);
        contenido.setAlignment(Pos.CENTER);
        UITools.setTamanoFijo(contenido, ANCHO_MODAL, ALTO_MODAL);
        contenido.setPickOnBounds(true);

        getChildren().addAll(fondoOscuro, contenido);
        setAlignment(Pos.CENTER);
        contenido.setOnMouseClicked(Event::consume);
    }

    public void mostrar(LevelData data, LevelInfoType type, Runnable onPlay, Runnable onReplay, Runnable onBack) {
        if (animando || visible) return;

        animando = true;
        visible = true;
        this.alJugar = onPlay;
        this.alReintentar = onReplay;
        this.alVolver = onBack;

        contenido.getChildren().clear();
        fondoNivelModal(data.getNivelNumero());
        construirModal(data, type);

        setVisible(true);
        setMouseTransparent(false);
        toFront();
        setOpacity(1);

        UITools.mostrarAnimacion(contenido, () -> animando = false);
    }

    private void fondoNivelModal(int nivelNumero) {
        String rutaImagen = MODALES_NIVELES[Math.min(nivelNumero - 1, MODALES_NIVELES.length - 1)];
        contenido.setStyle(
                "-fx-background-image: url('" + rutaImagen + "');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 40;" +
                        "-fx-border-color: transparent;"
        );
    }

    private void construirModal(LevelData data, LevelInfoType type) {
        switch (type) {
            case NIVEL_NUEVO -> modalNuevoNivel(data);
            case NIVEL_JUGADO -> modalNivelJugado(data);
            case NIVEL_COMPLETADO -> modalNivelCompletado(data);
        }
    }

    private void modalNuevoNivel(LevelData data) {
        ImageView imagenEstrellas = imagenEstrellas(getRutaImagenEstrellaModal(0));
        imagenEstrellas.setTranslateX(-6);
        imagenEstrellas.setTranslateY(115);

        Button botonJugar = UITools.imagenBoton("images/buttons/PlayGameButton.png", 200, 120);
        botonJugar.setTranslateX(-5);
        botonJugar.setTranslateY(97);
        botonJugar.setOnAction(e -> {
            if (alJugar != null) alJugar.run();
            ocultar();
        });

        Button botonCerrar = botonCerrar(375, -365);
        contenido.getChildren().addAll(imagenEstrellas, botonJugar, botonCerrar);
    }

    private void modalNivelJugado(LevelData data) {
        ImageView imagenEstrellas = imagenEstrellas(getRutaImagenEstrellaModal(data.getEstrellasObtenidas()));
        imagenEstrellas.setTranslateX(-6);
        imagenEstrellas.setTranslateY(110);

        Button botonReintentar = UITools.imagenBoton("images/buttons/ReplayButton.png", 200, 120);
        botonReintentar.setTranslateX(-3);
        botonReintentar.setTranslateY(110);
        botonReintentar.setOnAction(e -> {
            if (alReintentar != null) alReintentar.run();
            ocultar();
        });

        Button botonCerrar = botonCerrar(375, -365);
        contenido.getChildren().addAll(imagenEstrellas, botonReintentar, botonCerrar);
    }

    private void modalNivelCompletado(LevelData data) {
        ImageView imagenEstrellas = imagenEstrellas(getRutaImagenEstrellaModal(data.getEstrellasObtenidas()));
        imagenEstrellas.setTranslateX(-3);
        imagenEstrellas.setTranslateY(120);

        Button botonReintentar = UITools.imagenBoton("images/buttons/ReplayButton.png", 200, 120);
        botonReintentar.setTranslateX(-210);
        botonReintentar.setTranslateY(96);
        botonReintentar.setOnAction(e -> {
            if (alReintentar != null) alReintentar.run();
            ocultar();
        });

        Button botonVolver = UITools.imagenBoton("images/buttons/BackToMapButton.png", 200, 120);
        botonVolver.setTranslateX(190);
        botonVolver.setTranslateY(-20);
        botonVolver.setOnAction(e -> {
            if (alVolver != null) alVolver.run();
            ocultar();
        });

        contenido.getChildren().addAll(imagenEstrellas, botonReintentar, botonVolver);
    }

    private ImageView imagenEstrellas(String rutaImagen) {
        ImageView imagenEstrellas = UITools.crearVistaImagen(rutaImagen, 450, 350);
        imagenEstrellas.setPreserveRatio(true);
        return imagenEstrellas;
    }

    private Button botonCerrar(double x, double y) {
        Button botonCerrar = UITools.imagenBoton("images/buttons/CloseInfoButton.png", 80, 80);
        botonCerrar.setTranslateX(x);
        botonCerrar.setTranslateY(y);
        botonCerrar.setOnAction(e -> ocultar());
        return botonCerrar;
    }

    private String getRutaImagenEstrellaModal(int estrellas) {
        return switch (estrellas) {
            case 1 -> "images/stars/StarModal_1.png";
            case 2 -> "images/stars/StarModal_2.png";
            case 3 -> "images/stars/StarModal_3.png";
            default -> "images/stars/StarModal_0.png";
        };
    }

    public void ocultar() {
        if (animando || !visible) return;
        animando = true;
        visible = false;
        UITools.ocultarModal(this, contenido, () -> animando = false);
    }
}