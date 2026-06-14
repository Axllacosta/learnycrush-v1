package org.lc.v1.game.levels;

import javafx.animation.ScaleTransition;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class LevelNode extends StackPane {
    private final LevelData datos;
    private final ImageView icono;
    private final ImageView iconoEstrellas;
    private Runnable alClicCallback;

    public LevelNode(LevelData datos) {
        this.datos = datos;
        this.icono = new ImageView();
        this.iconoEstrellas = new ImageView();

        setPrefSize(75, 75);
        setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        icono.setFitWidth(75);
        icono.setFitHeight(75);
        iconoEstrellas.setFitWidth(78);
        iconoEstrellas.setFitHeight(45);
        iconoEstrellas.setTranslateY(-45);

        actualizarIcono();
        actualizarEstrellas();
        getChildren().addAll(icono, iconoEstrellas);
        configEventos();
    }

    private void configEventos() {
        if (datos.esAccesible()) {
            setOnMouseEntered(e -> {
                setCursor(Cursor.HAND);
                ScaleTransition st = new ScaleTransition(Duration.millis(100), this);
                st.setToX(1.15);
                st.setToY(1.15);
                st.play();
            });
            setOnMouseExited(e -> {
                setCursor(Cursor.DEFAULT);
                ScaleTransition st = new ScaleTransition(Duration.millis(100), this);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            });
            setOnMouseClicked(e -> {
                if (alClicCallback != null) alClicCallback.run();
            });
        }
    }

    private void actualizarIcono() {
        String rutaImagen = datos.esAccesible() ?
                String.format("images/levels/level_%d.png", datos.getNivelNumero()) :
                String.format("images/levels/level_%d_locked.png", datos.getNivelNumero());
        icono.setImage(new Image(rutaImagen));
    }

    private void actualizarEstrellas() {
        String rutaEstrellas;
        if (datos.getEstado() == LevelState.COMPLETADO && datos.getEstrellasObtenidas() > 0) {
            rutaEstrellas = switch (datos.getEstrellasObtenidas()) {
                case 1 -> "images/stars/1star.png";
                case 2 -> "images/stars/2star.png";
                case 3 -> "images/stars/3star.png";
                default -> "images/stars/0star.png";
            };
        } else {
            rutaEstrellas = "images/stars/0star.png";
        }
        iconoEstrellas.setImage(new Image(rutaEstrellas));
    }

    public void refrescar() {
        actualizarIcono();
        actualizarEstrellas();
    }

    public void alSeleccionar(Runnable callback) {
        this.alClicCallback = callback;
    }

    public LevelData getData() {
        return datos;
    }

    public LevelInfoType getModalTipo() {
        if (!datos.esAccesible()) return null;
        if (datos.getEstado() == LevelState.COMPLETADO) return LevelInfoType.NIVEL_JUGADO;
        return LevelInfoType.NIVEL_NUEVO;
    }
}