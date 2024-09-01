package com.oniverse.neon_dystopia;

import com.oniverse.neon_dystopia.vue.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The main class of the game.
 *
 * @version 0.0.1 Alpha
 */
public class  App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Menu menu = new Menu(700);

        Scene scene = new Scene(menu.getGroup(),
                700, 700);
        stage.setResizable(false);
        menu.draw(scene);

        stage.setTitle("Neon dystopia - V0.0.1 Alpha");
        stage.setScene(scene);
        stage.getIcons().add(
                new Image(
                    new FileInputStream("src/main/resources/com/oniverse/neon_dystopia/textures/icon/NeonDystopia.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}