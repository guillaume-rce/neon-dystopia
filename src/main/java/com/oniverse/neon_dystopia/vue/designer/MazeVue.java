package com.oniverse.neon_dystopia.vue.designer;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levels.block.Block;
import com.oniverse.neon_dystopia.model.levels.block.BlockId;
import com.oniverse.neon_dystopia.model.levels.block.def.AmbientLight;
import com.oniverse.neon_dystopia.model.utils.Link;
import com.oniverse.neon_dystopia.model.utils.LinkPoint;
import com.oniverse.neon_dystopia.vue.designer.others.InteractedMenuVue;
import com.oniverse.neon_dystopia.vue.utils.ImageVue;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.scene.Cursor;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

/**
 * This class is used to display a maze in the designer.
 */
public class MazeVue extends Vue {
    /**
     * The level designer.
     */
    private final LevelDesigner levelDesigner;
    /**
     * The list of blocks in the maze.
     */
    private ArrayList<Block> blocks;
    /**
     * The list of links in the maze.
     */
    private final ArrayList<Link> links;

    /**
     * The selected block.
     */
    private Rectangle selectedBlock;

    public final Group parametersGroup = new Group();

    /**
     * The size of a block on the screen.
     */
    private double displaySize;
    /**
     * The active layer.
     */
    private int activeLayer = 0;
    private int currentMazeIndex = 0;

    /**
     * This constructor. It's adding a listener to the maze and the properties.
     *
     * @param coordinate    The coordinate of the maze.
     * @param size          The size of the maze.
     * @param levelDesigner The level designer.
     */
    public MazeVue(Coordinate coordinate, int size, LevelDesigner levelDesigner) {
        super(coordinate, size, size);
        this.levelDesigner = levelDesigner;
        this.levelDesigner.addListener(this);
        this.levelDesigner.getCurrentMaze().addListener(this);
        this.levelDesigner.getCurrentMaze().getProperties().addListener(this);
        this.displaySize = this.boundingBox.getWidth() / this.levelDesigner.getCurrentMaze().getProperties().getWidth();
        this.blocks = this.levelDesigner.getCurrentMaze().getBlocks();
        this.links = this.levelDesigner.getLinks();
        this.currentMazeIndex = this.levelDesigner.getCurrentMaze().getProperties().getId();
        this.draw();
    }

    /**
     * Draw the maze.
     */
    public void draw() {
        this.group.getChildren().clear();
        // this.unDisplayParameters();
        // Draw a background
        Rectangle background = new javafx.scene.shape.Rectangle(
                this.boundingBox.getMinX(), this.boundingBox.getMinY(),
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
        background.setFill(null);
        background.setStrokeWidth(1);
        background.setStroke(javafx.scene.paint.Color.WHITE);
        background.setOpacity(0.8);
        this.group.getChildren().add(background);

        // Draw the blocks
        for (Block block : blocks) {
            ImageVue imageVue = copyImageVue(block);
            if (block.id == BlockId.AMBIENT_LIGHT) {
                AmbientLight ambientLight = (AmbientLight) block;
                this.drawAmbientLight(ambientLight);
            }
            this.group.getChildren().add(imageVue.getGroup());
        }

        for (Link link : links) {
            if (link.getSource().getMazeId() == link.getTarget().getMazeId() &&
                    link.getSource().getMazeId() == this.currentMazeIndex) {
                Line line = copyLine(link);
                this.group.getChildren().add(line);
            }
            if (levelDesigner.getMaze(link.getSource().getMazeId()).getBlock(link.getSource().getCoordinate()).isTransporter() &&
                    levelDesigner.getMaze(link.getTarget().getMazeId()).getBlock(link.getTarget().getCoordinate()).isTransporter()) {
                for (LinkPoint linkPoint : link.getLinkPoints())
                    if (linkPoint.getMazeId() == this.currentMazeIndex) {
                        Circle circle = getCircle(this.boundingBox.getMinX() +
                                linkPoint.getCoordinate().getX() * this.displaySize + this.displaySize / 2 + this.displaySize / 6, linkPoint, Color.RED);
                        this.group.getChildren().add(circle);
                    }
            } else {
                for (LinkPoint linkPoint : link.getLinkPoints()) {
                    if (linkPoint.getMazeId() == this.currentMazeIndex) {
                        Circle circle = getCircle(this.boundingBox.getMinX() +
                                linkPoint.getCoordinate().getX() * this.displaySize + this.displaySize / 4, linkPoint, (link.getSource() == linkPoint) ? Color.GREEN : Color.BLUE);
                        this.group.getChildren().add(circle);
                    }
                }
            }
        }
        this.group.getChildren().add(this.parametersGroup);
    }

    private Circle getCircle(double boundingBox, LinkPoint linkPoint, Color red) {
        Circle circle = new Circle((this.displaySize / 5 > 6) ? 6 : this.displaySize / 5);
        circle.setTranslateX(boundingBox);
        circle.setTranslateY(this.boundingBox.getMinY() +
                linkPoint.getCoordinate().getY() * this.displaySize + this.displaySize / 4);
        circle.setFill(red);
        circle.setOpacity((linkPoint.getCoordinate().getLayer() == this.activeLayer) ? 0.8 : 0.3);
        return circle;
    }

    private Line copyLine(Link link) {
        Line line = new Line(
                this.boundingBox.getMinX() + link.getSource().getCoordinate().getX() * this.displaySize + this.displaySize / 2,
                this.boundingBox.getMinY() + link.getSource().getCoordinate().getY() * this.displaySize + this.displaySize / 2,
                this.boundingBox.getMinX() + link.getTarget().getCoordinate().getX() * this.displaySize + this.displaySize / 2,
                this.boundingBox.getMinY() + link.getTarget().getCoordinate().getY() * this.displaySize + this.displaySize / 2
        );
        line.setStrokeWidth(1);
        line.setStroke(Color.WHITE);
        line.setOpacity(0.7);
        return line;
    }

    private ImageVue copyImageVue(Block block) {
        ImageVue imageVue = new ImageVue(
                block.getTexture().getTexture(),
                new Coordinate(
                        (int) (this.boundingBox.getMinX() + block.getCoordinate().getX() * this.displaySize),
                        (int) (this.boundingBox.getMinY() + block.getCoordinate().getY() * this.displaySize)
                ),
                (int) this.displaySize,
                (int) this.displaySize
        );
        imageVue.setOpacity((block.getCoordinate().getLayer() == this.activeLayer) ? 1 : 0.5);
        return imageVue;
    }

    private void drawAmbientLight(AmbientLight ambientLight) {
        Group lightGroup = new Group();
        Circle circle = getCircle(ambientLight, ambientLight.getStrength() * this.displaySize);
        lightGroup.getChildren().add(circle);
        Circle circle2 = getCircle(ambientLight, (double) ambientLight.getStrength() / 2 * this.displaySize);
        lightGroup.getChildren().add(circle2);
        this.group.getChildren().add(lightGroup);
    }

    private Circle getCircle(AmbientLight ambientLight, double ambientLight1) {
        Circle circle = new Circle(
                (int) (this.boundingBox.getMinX() +
                        ambientLight.getCoordinate().getX() * this.displaySize + this.displaySize / 2),
                (int) (this.boundingBox.getMinY() +
                        ambientLight.getCoordinate().getY() * this.displaySize + this.displaySize / 2),
                ambientLight1);
        circle.setFill(ambientLight.getColor());
        circle.setOpacity(0.2);
        circle.setEffect(new BoxBlur(10, 10, 3));
        return circle;
    }

    /**
     * Getter for the size of a block on the screen.
     *
     * @return The size of a block on the screen.
     */
    public double getDisplaySize() {
        return this.displaySize;
    }

    /**
     * Setter for the active layer.
     *
     * @param activeLayer The active layer.
     */
    public void setActiveLayer(int activeLayer) {
        this.activeLayer = activeLayer;
    }

    /**
     * Select a block.
     *
     * @param block The block to select.
     */
    public void selectBlock(Block block) {
        this.selectedBlock = new Rectangle(
                this.boundingBox.getMinX() + block.getCoordinate().getX() * this.displaySize,
                this.boundingBox.getMinY() + block.getCoordinate().getY() * this.displaySize,
                this.displaySize,
                this.displaySize
        );
        this.selectedBlock.setFill(null);
        this.selectedBlock.setStrokeWidth(1);
        this.selectedBlock.setStroke(Color.BLUE);
        this.selectedBlock.setOpacity(0.8);
        this.group.getChildren().add(this.selectedBlock);
    }

    /**
     * Unselect a block.
     */
    public void unselectBlock() {
        this.group.getChildren().remove(this.selectedBlock);
        // this.draw();
    }

    public void displayParameters(Block block){
        this.unDisplayParameters();
        double x = this.boundingBox.getMinX() + block.getCoordinate().getX() * this.displaySize + this.displaySize;
        double y = this.boundingBox.getMinY() + block.getCoordinate().getY() * this.displaySize;

        if (block.id == BlockId.AMBIENT_LIGHT) {
            this.selectBlock(block);
            Rectangle background = new Rectangle(x, y, 90, 55);
            background.setFill(Color.WHITE);
            background.setOpacity(0.8);
            this.parametersGroup.getChildren().add(background);

            // Colors
            InteractedMenuVue.Button button = new InteractedMenuVue.Button(
                    new Coordinate((int) (x + 5), (int) (y + 5)), 20,
                    25, "<<", "Color.previous");
            button.getGroup().getChildren().get(0).setStyle("-fx-arc-height: 3px; -fx-arc-width: 3px;");
            button.getGroup().getChildren().get(1).setStyle("-fx-font-size: 10px;");
            button.getGroup().getChildren().get(1).setLayoutX(5); button.getGroup().getChildren().get(1).setLayoutY(-1);
            button.getGroup().setOnMouseMoved((event) -> {
                button.setHovered(button.getGroup().contains(event.getX(), event.getY()));
            });
            button.getGroup().setOnMouseClicked((event) -> {
                button.getGroup().setCursor(Cursor.HAND);
                button.setHovered(true);
                ((AmbientLight) block).previousColor();
                this.draw();
                this.displayParameters(block);
            });
            Rectangle color = new Rectangle(x + 35, y + 5, 20, 20);
            color.setFill(((AmbientLight) block).getColor());
            color.setStroke(Color.BLACK);
            color.setStrokeWidth(1);
            InteractedMenuVue.Button button2 = new InteractedMenuVue.Button(
                    new Coordinate((int) (x + 60), (int) (y + 5)), 20,
                    25, ">>", "Color.next");
            button2.getGroup().getChildren().get(0).setStyle("-fx-arc-height: 3px; -fx-arc-width: 3px;");
            button2.getGroup().getChildren().get(1).setStyle("-fx-font-size: 10px;");
            button2.getGroup().getChildren().get(1).setLayoutX(5); button2.getGroup().getChildren().get(1).setLayoutY(-1);
            button2.getGroup().setOnMouseMoved((event) -> {
                button2.setHovered(button2.getGroup().contains(event.getX(), event.getY()));
            });
            button2.getGroup().setOnMouseClicked((event) -> {
                button2.getGroup().setCursor(Cursor.HAND);
                button2.setHovered(true);
                ((AmbientLight) block).nextColor();
                this.draw();
                this.displayParameters(block);
            });
            this.parametersGroup.getChildren().addAll(button.getGroup(), color, button2.getGroup());

            // Strength
            InteractedMenuVue.Button button4 = new InteractedMenuVue.Button(
                    new Coordinate((int) (x + 5), (int) (y + 30)), 20,
                    25, "-", "Strength.decrease");
            button4.getGroup().getChildren().get(0).setStyle("-fx-arc-height: 3px; -fx-arc-width: 3px;");
            button4.getGroup().getChildren().get(1).setStyle("-fx-font-size: 10px;");
            button4.getGroup().getChildren().get(1).setLayoutX(2); button4.getGroup().getChildren().get(1).setLayoutY(-1);
            button4.getGroup().setOnMouseMoved((event) -> {
                if (button4.getGroup().contains(event.getX(), event.getY())) {
                    button4.getGroup().setCursor(Cursor.HAND);
                    button4.setHovered(true);
                } else {
                    button4.getGroup().setCursor(Cursor.DEFAULT);
                    button4.setHovered(false);
                }});
            button4.getGroup().setOnMouseClicked((event) -> {
                button4.getGroup().setCursor(Cursor.HAND);
                button4.setHovered(true);
                ((AmbientLight) block).decreaseStrength();
                this.draw();
                this.displayParameters(block);
            });
            Text strength = new Text(x + 35, y + 47, Integer.toString(((AmbientLight) block).getStrength()));
            strength.setFont(new Font(20));
            InteractedMenuVue.Button button3 = new InteractedMenuVue.Button(
                    new Coordinate((int) (x + 60), (int) (y + 30)), 20,
                    25, "+", "Strength.increase");
            button3.getGroup().getChildren().get(0).setStyle("-fx-arc-height: 3px; -fx-arc-width: 3px;");
            button3.getGroup().getChildren().get(1).setStyle("-fx-font-size: 10px;");
            button3.getGroup().getChildren().get(1).setLayoutX(2); button3.getGroup().getChildren().get(1).setLayoutY(-1);
            button3.getGroup().setOnMouseMoved((event) -> {
                if (button3.getGroup().contains(event.getX(), event.getY())) {
                    button3.getGroup().setCursor(Cursor.HAND);
                    button3.setHovered(true);
                } else {
                    button3.getGroup().setCursor(Cursor.DEFAULT);
                    button3.setHovered(false);
                }});
            button3.getGroup().setOnMouseClicked((event) -> {
                button3.getGroup().setCursor(Cursor.HAND);
                button3.setHovered(true);
                ((AmbientLight) block).increaseStrength();
                this.draw();
                this.displayParameters(block);
            });
            this.parametersGroup.getChildren().addAll(button3.getGroup(), strength, button4.getGroup());

        }
    }

    public void unDisplayParameters(){
        this.parametersGroup.getChildren().clear();
        if (this.selectedBlock != null)
            this.unselectBlock();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("MazeDesigner.blocks")) {
            this.blocks = (ArrayList<Block>) evt.getNewValue();
        }
        if (evt.getPropertyName().equals("MazeProperties.width") ||
                evt.getPropertyName().equals("MazeProperties.height")) {
            this.displaySize = this.boundingBox.getWidth()/(int) evt.getNewValue();
        }
        if (evt.getPropertyName().equals("LevelDesigner.currentMazeIndex")) {
            this.levelDesigner.getMaze((int) evt.getOldValue()).removeListener(this);
            this.levelDesigner.getMaze((int) evt.getOldValue()).getProperties().removeListener(this);

            this.levelDesigner.getMaze((int) evt.getNewValue()).addListener(this);
            this.levelDesigner.getMaze((int) evt.getNewValue()).getProperties().addListener(this);

            this.displaySize = this.boundingBox.getWidth()/
                    this.levelDesigner.getMaze((int) evt.getNewValue()).getProperties().getWidth();
            this.blocks = this.levelDesigner.getMaze((int) evt.getNewValue()).getBlocks();
            this.currentMazeIndex = (int) evt.getNewValue();
        }
        this.unDisplayParameters();
        this.draw();
    }
}
