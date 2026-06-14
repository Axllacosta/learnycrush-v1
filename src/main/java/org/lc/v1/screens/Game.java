package org.lc.v1.screens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.lc.v1.App;
import org.lc.v1.database.Query;
import org.lc.v1.UITools;
import org.lc.v1.game.content.Content;
import org.lc.v1.game.content.ContentData;
import org.lc.v1.game.content.Question;
import org.lc.v1.game.levels.LevelData;
import org.lc.v1.game.levels.LevelInfoType;
import org.lc.v1.game.levels.LevelState;
import org.lc.v1.game.modals.LevelInfoModal;

import java.util.List;

public class Game {
    private final StackPane raiz;
    private final App aplicacion;
    private final int numeroNivel;
    private final List<Content> contenido;
    private int indiceActual = 0;
    private int respuestasCorrectas = 0;
    private boolean respondida = false;

    private ImageView imagenContenido;
    private HBox contenedorOpciones;
    private Button botonContinuar;
    private final LevelInfoModal modalInfoNivel;

    public Game(App app, int levelNumber) {
        this.aplicacion = app;
        this.numeroNivel = levelNumber;
        this.contenido = ContentData.getQuestions(levelNumber);
        this.raiz = new StackPane();
        this.modalInfoNivel = new LevelInfoModal();

        if (contenido.isEmpty()) {
            terminarNivel();
            return;
        }

        construirUI();
        mostrarContenidoActual();
    }

    private void construirUI() {
        raiz.setPrefSize(1280, 720);
        imagenContenido = new ImageView();
        imagenContenido.setFitWidth(1280);
        imagenContenido.setFitHeight(720);
        imagenContenido.setPreserveRatio(false);

        contenedorOpciones = new HBox(15);
        contenedorOpciones.setAlignment(Pos.CENTER);
        contenedorOpciones.setTranslateY(150);

        botonContinuar = UITools.imagenBoton("/images/buttons/ContinueButton.png", 200, 200);
        botonContinuar.setTranslateY(290);
        botonContinuar.setTranslateX(480);
        botonContinuar.setVisible(false);

        raiz.getChildren().addAll(imagenContenido, contenedorOpciones, botonContinuar, modalInfoNivel);
    }

    private void mostrarContenidoActual() {
        if (indiceActual >= contenido.size()) {
            terminarNivel();
            return;
        }

        Content c = contenido.get(indiceActual);
        try {
            imagenContenido.setImage(new Image(c.getImagenRuta()));
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
        }

        if (c.tieneOpciones()) {
            mostrarPregunta((Question) c);
        } else {
            mostrarLeccion();
        }
    }

    private void mostrarPregunta(Question q) {
        botonContinuar.setVisible(false);
        contenedorOpciones.setVisible(true);
        respondida = false;

        contenedorOpciones.getChildren().clear();
        List<String> opciones = q.getOpcionImagen();
        for (int i = 0; i < opciones.size(); i++) {
            final int indice = i;
            Button opcion = UITools.imagenBoton(opciones.get(i), 140, 100);
            opcion.setOnAction(e -> verificarRespuesta(indice, q));
            contenedorOpciones.getChildren().add(opcion);
        }
    }

    private void mostrarLeccion() {
        contenedorOpciones.setVisible(false);
        botonContinuar.setOnAction(e -> avanzar());
        botonContinuar.setVisible(true);
    }

    private void verificarRespuesta(int indiceSeleccionado, Question q) {
        if (respondida) return;
        respondida = true;
        if (q.esCorrecto(indiceSeleccionado)) {
            respuestasCorrectas++;
            mostrarFeedback(true);
        } else {
            mostrarFeedback(false);
        }
    }

    private void mostrarFeedback(boolean esCorrecta) {
        String ruta = esCorrecta ? "/images/content/feedback/correct.png" : "/images/content/feedback/incorrect.png";
        ImageView feedback = new ImageView(new Image(ruta));
        feedback.setFitWidth(1280);
        feedback.setFitHeight(720);
        feedback.setPreserveRatio(false);
        raiz.getChildren().add(feedback);

        botonContinuar.setOnAction(e -> {
            raiz.getChildren().remove(feedback);
            avanzar();
        });
        botonContinuar.setVisible(true);
        botonContinuar.toFront();
    }

    private void avanzar() {
        indiceActual++;
        mostrarContenidoActual();
    }

    private void terminarNivel() {
        Query.saveProgress(numeroNivel, respuestasCorrectas);
        mostrarModalNivelCompletado(respuestasCorrectas);
    }

    private void mostrarModalNivelCompletado(int estrellas) {
        LevelData datos = new LevelData(numeroNivel, LevelState.COMPLETADO, estrellas);
        modalInfoNivel.mostrar(
                datos,
                LevelInfoType.NIVEL_COMPLETADO,
                () -> { modalInfoNivel.ocultar(); reiniciarNivel(); },
                () -> { modalInfoNivel.ocultar(); reiniciarNivel(); },
                () -> {
                    modalInfoNivel.ocultar();
                    if (numeroNivel == 5) aplicacion.mostrarCredits();
                    else aplicacion.mostrarMap();
                }
        );
    }

    private void reiniciarNivel() {
        indiceActual = 0;
        respuestasCorrectas = 0;
        respondida = false;
        mostrarContenidoActual();
    }

    public StackPane getVista() {
        return raiz;
    }
}