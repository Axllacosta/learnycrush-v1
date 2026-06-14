package org.lc.v1;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.lc.v1.database.Database;
import org.lc.v1.screens.Credits;
import org.lc.v1.screens.Game;
import org.lc.v1.screens.MainMenu;
import org.lc.v1.screens.Map;

public class App extends Application {

    private Stage mainStage;
    private static final int ANCHO = 1280;
    private static final int ALTO = 720;

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;
        mainStage.setTitle("Learny Crush");
        mainStage.setResizable(false);
        mainStage.setWidth(ANCHO);
        mainStage.setHeight(ALTO);

        Image icono = new Image("/images/lc-icon.png");
        stage.getIcons().add(icono);

        Database.initialize();

        mostrarBootLoading();
    }

    private void mostrarBootLoading() {
        StackPane bootRoot = new StackPane();
        bootRoot.setPrefSize(ANCHO, ALTO);

        ImageView bootImage = UITools.fondo("/images/bootLoading.png", ANCHO, ALTO);
        bootRoot.getChildren().add(bootImage);

        Scene bootScene = new Scene(bootRoot, ANCHO, ALTO);
        mainStage.setScene(bootScene);
        mainStage.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> mostrarMainMenu());
        pause.play();
    }

    public void mostrarMainMenu() {
        MainMenu menu = new MainMenu(this);
        Scene scene = new Scene(menu.getVista(), ANCHO, ALTO);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void mostrarMap() {
        Map map = new Map(this);
        Scene scene = new Scene(map.getVista(), ANCHO, ALTO);
        mainStage.setScene(scene);
    }

    public void mostrarLevel(int levelNumber) {
        Game game = new Game(this, levelNumber);
        Scene scene = new Scene(game.getVista(), ANCHO, ALTO);
        mainStage.setScene(scene);
    }

    public void mostrarCredits() {
        Credits credits = new Credits(this);
        Scene scene = new Scene(credits.getView(), ANCHO, ALTO);
        mainStage.setScene(scene);
    }

    public static void main(String[] args) {
        Database.initialize();
        launch(args);
    }
}