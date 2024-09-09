package com.oniverse.neon_dystopia.vue;

import com.oniverse.neon_dystopia.control.MenuControl;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.vue.utils.Button;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

/**
 * The start menu of the game.
 * TODO: Need to be refactored.
 */
public class Menu {
    /**
     * The group of the menus.
     */
    private final Group group;
    /**
     * The size of the window.
     */
    private final int windowSize;

    /**
     * Creates a new menu.
     * @param size the size of the window
     */
    public Menu(int size) {
        this.group = new Group();
        this.windowSize = size;
    }

    /**
     * Draws the menu.
     * @param scene the scene to draw on
     */
    public void draw(Scene scene) {
        this.group.getChildren().clear();
        Rectangle rectangle = new Rectangle(0, 0, this.windowSize, this.windowSize);

        // Start button
        this.group.getChildren().add(rectangle);

        ImageVue logo = new ImageVue("/com/oniverse/neon_dystopia/textures/icon/NeonDystopia.png",
                new Coordinate(this.windowSize / 2 - 200, -10), 400, 170, false);
        this.group.getChildren().add(logo.getGroup());

        /*
        Button button = new Button(new Coordinate(this.windowSize / 2 - 75, this.windowSize / 4 - 20),
                150, 40, "History mode", null, 50, 15);
        //deactivate the button
        button.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-opacity: 0.5; ");
        this.group.getChildren().add(button.getGroup());
         */

        // Level designer button
        Button button = new Button(new Coordinate(this.windowSize / 2 - 200, this.windowSize / 2 - 20),
                400, 40, "Level designer",
                () -> MenuControl.levelDesigner(scene, this.windowSize), 50, 15);
        this.group.getChildren().add(button.getGroup());

        // From file button
        button = new Button(new Coordinate(this.windowSize / 2 - 100, this.windowSize * 3 / 4 - 20),
                200, 40, "Load another map",
                () -> MenuControl.fromFile(scene, this.windowSize), 50, 15);
        this.group.getChildren().add(button.getGroup());
    }

    /**
     * Returns the group of the menu.
     *
     * @return the group of the menu
     */
    public Group getGroup() {
        return this.group;
    }
}
