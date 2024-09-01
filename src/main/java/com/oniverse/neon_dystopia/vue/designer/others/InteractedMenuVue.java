package com.oniverse.neon_dystopia.vue.designer.others;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levelDesigner.Maze;
import com.oniverse.neon_dystopia.model.utils.Link;
import com.oniverse.neon_dystopia.model.utils.MapsGetter;
import com.oniverse.neon_dystopia.model.utils.XMLReader;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;


public class InteractedMenuVue extends Vue {
    private static final int NONE_MODE = -1;
    private static final int LINKS_MODE = 0;
    private static final int MAZES_MODE = 1;
    private static final int LEVELS_MODE = 2;

    public ArrayList<Button> buttons;
    public ListView<Link> linksVue = null;
    public ListView<XMLReader> levelsVue = null;
    private int mode = NONE_MODE;
    private final LevelDesigner levelDesigner;

    public InteractedMenuVue(Coordinate coordinate, int height, int width, LevelDesigner levelDesigner) {
        super(coordinate, height, width);
        this.levelDesigner = levelDesigner;
        this.levelDesigner.addListener(this);
        this.draw();
    }

    /**
     * Draw the home menu.
     */
    @Override
    public void draw() {
        this.clear();
        this.drawBackground();

        Button links = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + 10, (int) this.boundingBox.getMinY() + 10),
                30, (int) this.boundingBox.getWidth() - 20, "Design links", "HOME_LINKS");
        Button mazes = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + 10,
                        (int) (this.boundingBox.getMinY() + links.getBoundingBox().getHeight() + 20)),
                30, (int) this.boundingBox.getWidth() - 20, "Mazes", "HOME_MAZES");
        Button levels = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + 10,
                        (int) (this.boundingBox.getMinY() + links.getBoundingBox().getHeight() +
                                mazes.getBoundingBox().getHeight() + 30)),
                30, (int) this.boundingBox.getWidth() - 20, "Load level", "HOME_LEVELS");
        Button export = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + 10,
                        (int) (this.boundingBox.getMinY() + links.getBoundingBox().getHeight() +
                                mazes.getBoundingBox().getHeight() + levels.getBoundingBox().getHeight() + 40)),
                30, (int) this.boundingBox.getWidth() - 20, "Export", "HOME_EXPORT");
        this.group.getChildren().addAll(links.getGroup(),
                mazes.getGroup(), levels.getGroup(), export.getGroup());
        this.buttons = new ArrayList<>(){{
            add(links);
            add(mazes);
            add(levels);
            add(export);
        }};
    }

    /**
     * Draw the link mode. Allow the user to add or remove links.
     * @param links The links of the level
     */
    public void linksMode(ArrayList<Link> links) {
        this.clear();
        this.drawBackground();
        mode = LINKS_MODE;

        Button addLink = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + 10, (int) this.boundingBox.getMinY() + 10),
                30, (int) this.boundingBox.getWidth() - 20, "Add link", "LINK_ADD");
        this.buttons = new ArrayList<>(){{
            add(addLink);
        }};
        ObservableList<Link> items = FXCollections.observableArrayList();
        items.addAll(links);
        linksVue = new ListView<>();
        linksVue.setItems(items);
        linksVue.setPrefSize(this.boundingBox.getWidth() - 20,
                this.boundingBox.getHeight() - addLink.getBoundingBox().getHeight() - 30);
        linksVue.setLayoutX(this.boundingBox.getMinX() + 10);
        linksVue.setLayoutY(this.boundingBox.getMinY() + addLink.getBoundingBox().getHeight() + 20);
        this.group.getChildren().addAll(addLink.getGroup(), linksVue);
    }

    /**
     * Draw the maze mode.The goal of this mode is to allow the user to select the mazes he wants to modify and to add or remove mazes.
     * @param numberOfMazes The number of mazes in the level
     * @param currentMaze The current maze selected
     */
    public void mazeMode(int numberOfMazes, int currentMaze) {
        this.clear();
        this.drawBackground();
        mode = MAZES_MODE;

        // Maze selector
        Button previous = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + 10, (int) this.boundingBox.getMinY() + 10),
                20, (int) this.boundingBox.getWidth() / 3 - 30, "<<", "MAZE_PREVIOUS");
        Text text = new Text("Current maze: " + currentMaze);
        text.setFont(Font.font("Verdana", 16));
        text.setX(this.boundingBox.getMinX() + (
                this.boundingBox.getWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY(this.boundingBox.getMinY() + 27);
        Button next = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + (int) this.boundingBox.getWidth() / 3 * 2 + 20,
                        (int) this.boundingBox.getMinY() + 10),
                20, (int) this.boundingBox.getWidth() / 3 - 30, ">>", "MAZE_NEXT");
        next.setAble(currentMaze < numberOfMazes);
        previous.setAble(currentMaze > 1);
        this.buttons = new ArrayList<>(){{
            add(previous);
            add(next);
        }};
        this.group.getChildren().addAll(previous.getGroup(), text, next.getGroup());

        // Number of mazes
        Text text2 = new Text("Number of mazes: " + numberOfMazes);
        text2.setFont(Font.font("Verdana", 16));
        text2.setX(this.boundingBox.getMinX() + (
                this.boundingBox.getWidth() - text2.getLayoutBounds().getWidth()) / 2);
        text2.setY(text.getLayoutBounds().getMinY() + text.getLayoutBounds().getHeight() + 26);
        Button remove = new Button(
                new Coordinate((int) this.boundingBox.getMinX() + 10,
                        (int) this.boundingBox.getMinY() + 40),
                20, (int) (this.boundingBox.getMinX() + text2.getLayoutBounds().getMinX() - 40),
                "-", "MAZE_REMOVE");
        Button add = new Button(
                new Coordinate((int) text2.getLayoutBounds().getMaxX() + 10,
                        (int) this.boundingBox.getMinY() + 40),
                20, (int) (this.boundingBox.getMaxX() - text2.getLayoutBounds().getMaxX() - 20),
                "+", "MAZE_ADD");
        remove.setAble(numberOfMazes > 1);
        this.buttons.add(remove);
        this.buttons.add(add);
        this.group.getChildren().addAll(remove.getGroup(), text2, add.getGroup());
    }

    /**
     * Draw the level mode. Allow the user to select a level (store in the resources folder) and to modify it.
     */
    public void levelsMode() {
        this.clear();
        this.drawBackground();
        mode = LEVELS_MODE;

        // Draw title
        Text text = new Text("Select a level");
        text.setFont(Font.font("Verdana", 16));
        text.setX(this.boundingBox.getMinX() + (
                this.boundingBox.getWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY(this.boundingBox.getMinY() + 27);
        this.group.getChildren().add(text);

        // Draw levels
        MapsGetter mapsGetter = new MapsGetter();
        ObservableList<XMLReader> items = FXCollections.observableArrayList();
        items.addAll(mapsGetter.getPlayerMaps());

        this.levelsVue = new ListView<>();
        this.levelsVue.setItems(items);
        this.levelsVue.setPrefSize(this.boundingBox.getWidth() - 20,
                this.boundingBox.getHeight() - text.getLayoutBounds().getHeight() - 30);
        this.levelsVue.setLayoutX(this.boundingBox.getMinX() + 10);
        this.levelsVue.setLayoutY(this.boundingBox.getMinY() + text.getLayoutBounds().getHeight() + 20);
        this.group.getChildren().add(this.levelsVue);
    }

    private void drawBackground() {
        Rectangle background = new Rectangle(this.boundingBox.getMinX(), this.boundingBox.getMinY(),
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
        background.setFill(Color.WHITE);
        background.setStrokeWidth(1);
        background.setStroke(Color.BLACK);
        background.setArcHeight(10);
        background.setArcWidth(10);
        this.group.getChildren().add(background);
    }

    public static class Button extends Vue {
        private final String text;
        private final String name;
        private boolean isAble = true;
        public Button(Coordinate coordinate, int height, int width, String text, String name) {
            super(coordinate, height, width);
            this.text = text;
            this.name = name;
            this.draw();
        }

        @Override
        public void draw() {
            this.group.getChildren().clear();
            Rectangle background = new Rectangle(this.boundingBox.getMinX(), this.boundingBox.getMinY(),
                    this.boundingBox.getWidth(), this.boundingBox.getHeight());
            background.setFill(Color.GRAY);
            background.setStrokeWidth(1);
            background.setStroke(Color.WHITE);
            background.setArcHeight(10);
            background.setArcWidth(10);

            Text text = new Text(this.text);
            text.setFont(Font.font("Verdana", 16));
            text.setX(this.boundingBox.getMinX() + (
                    this.boundingBox.getWidth() - text.getLayoutBounds().getWidth()) / 2);
            text.setY(this.boundingBox.getMinY() + (
                    this.boundingBox.getHeight() + text.getLayoutBounds().getHeight()) / 2 - 5);
            this.group.getChildren().addAll(background, text);
            this.group.setOpacity(0.7);
        }

        public void setHovered(boolean hovered) {
            if (this.isAble)
                if (hovered) {
                    this.group.setOpacity(1);
                    this.group.setCursor(Cursor.HAND);
                } else {
                    this.group.setOpacity(0.7);
                    this.group.setCursor(Cursor.DEFAULT);
                }
        }

        public void setAble(boolean able) {
            if (able) {
                this.group.setOpacity(1);
                this.isAble = true;
            } else {
                this.group.setOpacity(0.3);
                this.isAble = false;
            }
        }

        public boolean isAble() {
            return this.isAble;
        }

        public String getName() {
            return this.name;
        }
    }

    public void setHovered(int x, int y) {
        if (this.buttons != null)
            for (Button button: this.buttons)
                button.setHovered(button.getBoundingBox().contains(x, y));
    }

    public void setHeight(int height) {
        this.boundingBox = new BoundingBox(this.boundingBox.getMinX(), this.boundingBox.getMinY(),
                this.boundingBox.getWidth(), height);
    }

    public void clear() {
        this.group.getChildren().clear();
        this.linksVue = null;
        this.buttons = null;
        mode = NONE_MODE;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("LevelDesigner.links") && mode == LINKS_MODE)
            this.linksMode((ArrayList<Link>) evt.getNewValue());

        if (evt.getPropertyName().equals("LevelDesigner.mazes") && mode == MAZES_MODE)
            this.mazeMode(((ArrayList<Maze>) evt.getNewValue()).size(),
                    this.levelDesigner.getCurrentMaze().getProperties().getId() + 1);

        if (evt.getPropertyName().equals("LevelDesigner.currentMazeIndex") && mode == MAZES_MODE)
            this.mazeMode(this.levelDesigner.getMazes().size(),
                    (int) evt.getNewValue() + 1);
    }
}
