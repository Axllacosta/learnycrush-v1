package org.lc.v1;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.layout.Region;

public class UITools {

    public static Button imagenBoton(String ruta, double ancho, double alto) {
        ImageView imagen = new ImageView(ruta);
        imagen.setFitWidth(ancho);
        imagen.setFitHeight(alto);
        imagen.setPreserveRatio(true);

        Button boton = new Button("", imagen);
        boton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 0;");
        addHoverEffect(boton);
        return boton;
    }

    public static void addHoverEffect(Button boton) {
        boton.setOnMouseEntered(e -> {
            ScaleTransition escala = new ScaleTransition(Duration.millis(150), boton);
            escala.setToX(1.1);
            escala.setToY(1.1);
            escala.play();
        });

        boton.setOnMouseExited(e -> {
            ScaleTransition escala = new ScaleTransition(Duration.millis(150), boton);
            escala.setToX(1.0);
            escala.setToY(1.0);
            escala.play();
        });
    }

    public static ImageView crearVistaImagen(String ruta, double ancho, double alto) {
        ImageView imagen = new ImageView(ruta);
        imagen.setFitWidth(ancho);
        imagen.setFitHeight(alto);
        imagen.setPreserveRatio(true);
        return imagen;
    }

    public static ImageView fondo(String ruta, double ancho, double alto) {
        ImageView fondo = new ImageView(ruta);
        fondo.setFitWidth(ancho);
        fondo.setFitHeight(alto);
        return fondo;
    }

    public static void setPosicion(Node nodo, double x, double y) {
        nodo.setLayoutX(x);
        nodo.setLayoutY(y);
    }

    public static void setTamanoFijo(Region nodo, double ancho, double alto) {
        nodo.setPrefSize(ancho, alto);
        nodo.setMinSize(ancho, alto);
        nodo.setMaxSize(ancho, alto);
    }

    public static void ocultarModal(StackPane modal, VBox contenido, Runnable alOcultar) {
        FadeTransition desvanecer = new FadeTransition(Duration.millis(150), modal);
        desvanecer.setFromValue(1);
        desvanecer.setToValue(0);
        desvanecer.setOnFinished(e -> {
            modal.setVisible(false);
            modal.setMouseTransparent(true);
            contenido.getChildren().clear();
            modal.setOpacity(0);
            if (alOcultar != null) alOcultar.run();
        });
        desvanecer.play();
    }

    public static void mostrarAnimacion(Node nodo, Runnable alTerminar) {
        nodo.setScaleX(0.8);
        nodo.setScaleY(0.8);
        nodo.setOpacity(0);

        ScaleTransition escala = new ScaleTransition(Duration.millis(200), nodo);
        escala.setToX(1.0);
        escala.setToY(1.0);

        FadeTransition desvanecer = new FadeTransition(Duration.millis(200), nodo);
        desvanecer.setToValue(1);

        if (alTerminar != null) {
            desvanecer.setOnFinished(e -> alTerminar.run());
        }

        escala.play();
        desvanecer.play();
    }

    public static StackPane crearOverlay(double ancho, double alto, double opacidad) {
        StackPane overlay = new StackPane();
        overlay.setPrefSize(ancho, alto);
        Rectangle rectangulo = new Rectangle(ancho, alto, Color.rgb(0, 0, 0, opacidad));
        overlay.getChildren().add(rectangulo);
        overlay.setPickOnBounds(false);
        return overlay;
    }
}