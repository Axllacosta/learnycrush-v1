package org.lc.v1.screens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.lc.v1.App;
import org.lc.v1.UITools;

public class Credits {
    private final StackPane raiz;
    private final App aplicacion;

    public Credits(App app) {
        this.aplicacion = app;
        this.raiz = new StackPane();
        construirUI();
    }

    private void construirUI() {
        raiz.setPrefSize(1280, 720);

        ImageView fondo = UITools.fondo("/images/creditsBackground.png", 1280, 720);
        raiz.getChildren().add(fondo);

        VBox contenedor = new VBox(30);
        contenedor.setAlignment(Pos.CENTER);

        Button botonMenu = UITools.imagenBoton("/images/buttons/BackToMapButton.png", 200, 100);
        botonMenu.setTranslateY(230);
        botonMenu.setOnAction(e -> aplicacion.mostrarMainMenu());

        contenedor.getChildren().addAll(botonMenu);
        raiz.getChildren().add(contenedor);
    }

    public StackPane getView() {
        return raiz;
    }
}