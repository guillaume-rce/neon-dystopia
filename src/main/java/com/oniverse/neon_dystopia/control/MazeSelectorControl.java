package com.oniverse.neon_dystopia.control;

import com.oniverse.neon_dystopia.model.entities.Player;
import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levels.Level;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.maze.Maze;
import com.oniverse.neon_dystopia.model.utils.MazesFeed;
import com.oniverse.neon_dystopia.model.utils.XMLReader;
import com.oniverse.neon_dystopia.vue.Menu;
import com.oniverse.neon_dystopia.vue.entities.info.PlayerInfoVue;
import com.oniverse.neon_dystopia.vue.levels.LevelVue;
import com.oniverse.neon_dystopia.vue.mapSelection.MapSelectionVue;
import com.oniverse.neon_dystopia.vue.utils.Button;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MazeSelectorControl {
    private final MapSelectionVue mapSelectionVue;
    private final Scene scene;
    private final int size;
    private XMLReader selectedMap;
    private final MazesFeed feed = new MazesFeed();
    private boolean finded = false;
    private Group root;

    public MazeSelectorControl(MapSelectionVue mapSelectionVue, Scene scene, int size) {
        this.mapSelectionVue = mapSelectionVue;
        this.scene = scene;
        this.size = size;
        this.mapSelectionVue.getMapSelectorVue().getList().setOnMouseClicked(this::onClick);
    }

    public void onClick(MouseEvent event) {
        System.out.println("Click");
        if (event.getButton().equals(MouseButton.PRIMARY)){
            XMLReader selectedLevel = this.mapSelectionVue.getMapSelectorVue().getList().getSelectionModel().getSelectedItem();
            if (selectedLevel != null) {
                try {
                    Level level = new Level(
                            selectedLevel.getMazesForGame(),
                            selectedLevel.getProperties(), size);
                    level.setLinks(selectedLevel.getLinks(false));
                    // Update blocks
                    for (Maze maze : level.getMazes())
                        for (Block block : maze.getBlocks())
                            block.update();

                    this.feed.addLevel(level);
                    this.finded = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.finded)
            try {
                LevelVue levelVue = new LevelVue(this.feed.getCurrentLevel(), size);
                PlayerInfoVue playerInfoVue = new PlayerInfoVue(this.feed.getCurrentLevel().getPlayer(), size);
                int zoomCoefficient = 8;
                PlayerControl control = new PlayerControl(levelVue);
                control.linkMovement(scene);

                PerspectiveCamera camera = new PerspectiveCamera(false);
                camera.setTranslateZ((double) this.feed.getCurrentLevel().getCurrentMaze().getWidth() * zoomCoefficient);
                camera.setTranslateY(this.feed.getCurrentLevel().getPlayer().getX() - (double) size /2);
                camera.setTranslateX(this.feed.getCurrentLevel().getPlayer().getY() - (double) size /2);
                // Link camera to player
                this.feed.getCurrentLevel().getPlayer().addListener((evt) -> {
                    if (evt.getPropertyName().equals("Player.vuePosition")) {
                        camera.setTranslateX(((Player) evt.getSource()).getY() - (double) size / 2);
                        camera.setTranslateY(((Player) evt.getSource()).getX() - (double) size / 2);
                    }
                });

                this.feed.getCurrentLevel().addListener((evt) -> {
                    if (evt.getPropertyName().equals("Level.maze")) {
                        int mazeSize = ((Maze) evt.getNewValue()).getWidth();
                        camera.setTranslateZ((double) mazeSize * zoomCoefficient);
                    }
                    if (evt.getPropertyName().equals("Level.playerDied")) {
                        this.displayEnd(false);
                        control.stop(scene);
                    }
                    if (evt.getPropertyName().equals("Level.playerWin")) {
                        this.displayEnd(true);
                        control.stop(scene);
                    }
                });

                SubScene subScene = new SubScene(new Group(levelVue.getGroup()), size, size);
                subScene.setCamera(camera);

                root = new Group(subScene, playerInfoVue.getGroup());
                scene.setRoot(root);
                scene.setFill(Color.BLACK);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void displayEnd (boolean win) {
        Group group = new Group();
        Rectangle background = new Rectangle(30, 30, this.size-60, this.size-60);
        background.setFill(Color.BLACK);
        background.setOpacity(0.7);
        background.setArcHeight(5); background.setArcWidth(5);
        background.setStrokeWidth(2); background.setStroke(Color.WHITE);
        group.getChildren().add(background);

        Text text = new Text(win ? "You win !" : "You lose !");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 80px;");
        text.setX(60);
        text.setY((double) this.size /2 - text.getLayoutBounds().getHeight()/2);
        group.getChildren().add(text);

        Button button = new Button(new Coordinate(this.size /2 - 50, this.size /2 + 50), 100, 30, "Back to menu",
                () -> {
                    Menu menu = new Menu(this.size);
                    scene.setRoot(menu.getGroup());
                    menu.draw(scene);
                });
        group.getChildren().add(button.getGroup());

        root.getChildren().add(group);
    }
}
