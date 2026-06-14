package org.lc.v1.screens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.lc.v1.App;
import org.lc.v1.UITools;

public class MainMenu {
    private final StackPane raiz;
    private final App aplicacion;

    public MainMenu(App app) {
        this.aplicacion = app;
        this.raiz = new StackPane();
        construirUI();
    }

    private void construirUI() {
        ImageView fondo = UITools.fondo("/images/mainMenu.png", 1280, 720);

        VBox contenedorBotones = new VBox(30);
        contenedorBotones.setAlignment(Pos.CENTER);

        Button botonJugar = UITools.imagenBoton("/images/buttons/playButton.png", 350, 250);
        botonJugar.setTranslateY(100);
        botonJugar.setOnAction(e -> aplicacion.mostrarMap());

        contenedorBotones.getChildren().addAll(botonJugar);
        raiz.getChildren().addAll(fondo, contenedorBotones);
    }

    public StackPane getVista() {
        return raiz;
    }
}