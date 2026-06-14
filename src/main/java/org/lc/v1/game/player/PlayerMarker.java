package org.lc.v1.game.player;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PlayerMarker {
    private final ImageView vista;
    private Timeline animacion;

    public PlayerMarker() {
        this.vista = new ImageView("/images/Sunfire.png");
        vista.setFitWidth(120);
        vista.setFitHeight(120);
        vista.setPreserveRatio(true);
        iniciarAnimacion();
    }

    private void iniciarAnimacion() {
        animacion = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(vista.scaleXProperty(), 1.0),
                        new KeyValue(vista.scaleYProperty(), 1.0)
                ),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(vista.scaleXProperty(), 1.1),
                        new KeyValue(vista.scaleYProperty(), 1.1)
                ),
                new KeyFrame(Duration.seconds(3.0),
                        new KeyValue(vista.scaleXProperty(), 1.0),
                        new KeyValue(vista.scaleYProperty(), 1.0)
                )
        );
        animacion.setCycleCount(Animation.INDEFINITE);
        animacion.play();
    }

    public void moverA(Point2D destino, Runnable alFinalizar) {
        if (animacion != null) animacion.pause();

        Timeline animacionMovimiento = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(vista.layoutXProperty(), destino.getX(), Interpolator.EASE_BOTH),
                        new KeyValue(vista.layoutYProperty(), destino.getY(), Interpolator.EASE_BOTH)
                )
        );

        animacionMovimiento.setOnFinished(e -> {
            if (animacion != null) animacion.play();
            if (alFinalizar != null) alFinalizar.run();
        });

        animacionMovimiento.play();
    }

    public void setPosicion(Point2D pos) {
        vista.setLayoutX(pos.getX());
        vista.setLayoutY(pos.getY());
    }

    public ImageView getVista() {
        return vista;
    }
}