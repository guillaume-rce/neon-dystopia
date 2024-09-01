package com.oniverse.neon_dystopia.vue.designer;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.vue.designer.others.InteractedMenuButtonVue;
import com.oniverse.neon_dystopia.vue.designer.others.LayersSelectorVue;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MazeDesignerVue extends Vue {
    private final LevelDesigner levelDesigner;
    private MazeVue mazeVue;
    private BlocksVue blocksVue;
    private LayersSelectorVue layersSelectorVue;
    private PropertiesVue propertiesVue;
    private InteractedMenuButtonVue interactedMenuButtonVue;
    private Group textGroup;

    public MazeDesignerVue(Coordinate coordinate, int height, int width, LevelDesigner levelDesigner) {
        super(coordinate, height, width);
        this.levelDesigner = levelDesigner;
        this.draw();
    }

    public void draw() {
        this.group.getChildren().clear();
        this.group.getChildren().add(new Rectangle(
                this.getBoundingBox().getMinX(), this.getBoundingBox().getMinY(),
                this.getBoundingBox().getWidth(), this.getBoundingBox().getHeight()));

        this.interactedMenuButtonVue = new InteractedMenuButtonVue(new Coordinate(10, 10),
                (int) this.boundingBox.getHeight(), this.levelDesigner);

        this.mazeVue = new MazeVue(
                new Coordinate((int) this.interactedMenuButtonVue.getBoundingBox().getMaxX() + 10, 10),
                (int) (this.boundingBox.getWidth() - 210),
                this.levelDesigner);
        this.group.getChildren().add(this.mazeVue.getGroup());

        this.blocksVue = new BlocksVue(new Coordinate((int) this.mazeVue.getBoundingBox().getMaxX() + 10, 10),
                (int) (this.boundingBox.getHeight()-20), 55, 10);
        this.group.getChildren().add(this.blocksVue.getGroup());

        this.propertiesVue = new PropertiesVue(
                new Coordinate(10, (int) this.mazeVue.getBoundingBox().getMaxY() + 10),
                (int) (this.boundingBox.getHeight() - this.mazeVue.getBoundingBox().getHeight() - 30),
                (int) (this.boundingBox.getWidth() - this.blocksVue.getBoundingBox().getWidth() - 30),
                this.levelDesigner);
        this.group.getChildren().add(this.propertiesVue.getGroup());

        this.layersSelectorVue = new LayersSelectorVue(new Coordinate(
                (int) (this.propertiesVue.getBoundingBox().getMaxX() - 130),
                (int) (this.propertiesVue.getBoundingBox().getMinY() + 7)));
        this.layersSelectorVue.setMinMax(1, this.levelDesigner.getCurrentMaze().getProperties().getLayers());
        this.levelDesigner.getCurrentMaze().getProperties().addListener(this.layersSelectorVue);
        this.group.getChildren().add(this.layersSelectorVue.getGroup());

        this.interactedMenuButtonVue.setHeight(
                (int) (this.boundingBox.getHeight() - this.interactedMenuButtonVue.getBoundingBox().getHeight() -
                        this.propertiesVue.getBoundingBox().getHeight() - 40));
        this.group.getChildren().add(this.interactedMenuButtonVue.getGroup());
    }

    public LevelDesigner getLevelDesigner() {
        return this.levelDesigner;
    }

    public BlocksVue getBlocksVue() {
        return this.blocksVue;
    }

    public MazeVue getMazeVue() {
        return this.mazeVue;
    }

    public LayersSelectorVue getLayersSelectorVue() {
        return this.layersSelectorVue;
    }

    public PropertiesVue getPropertiesVue() {
        return this.propertiesVue;
    }

    public InteractedMenuButtonVue getInteractedMenuButtonVue() {
        return this.interactedMenuButtonVue;
    }

    public void displayText(Coordinate coordinate, String text) {
        if (this.textGroup != null && ((Text) this.textGroup.getChildren().get(1)).getText().equals(text)) {
            this.textGroup.setLayoutX(coordinate.getX());
            this.textGroup.setLayoutY(coordinate.getY());
        } else {
            this.removeText();
            this.textGroup = new Group();
            Text textNode = new Text(text.replace("_", " "));
            textNode.setX(5);
            Rectangle rectangle = new Rectangle(textNode.getLayoutBounds().getWidth()+10,
                    textNode.getLayoutBounds().getHeight() + 4);
            rectangle.setOpacity(0.8);
            rectangle.setFill(Color.WHITE);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
            rectangle.setY(-textNode.getLayoutBounds().getHeight()+1);
            this.textGroup.getChildren().addAll(rectangle, textNode);
            this.textGroup.setLayoutX(coordinate.getX());
            this.textGroup.setLayoutY(coordinate.getY());
            this.group.getChildren().add(this.textGroup);
        }
    }

    public void removeText() {
        if (this.textGroup != null)
            this.group.getChildren().remove(this.textGroup);
        this.textGroup = null;
        this.blocksVue.draw();
    }
}
