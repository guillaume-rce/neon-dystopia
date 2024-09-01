package com.oniverse.neon_dystopia.vue.designer;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.levelDesigner.LevelDesigner;
import com.oniverse.neon_dystopia.model.levelDesigner.Maze;
import com.oniverse.neon_dystopia.vue.utils.Vue;
import javafx.scene.Group;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.beans.PropertyChangeEvent;

public class PropertiesVue extends Vue {
    private final LevelDesigner levelDesigner;
    private final Group nameGroup = new Group(); public TextField nameField;
    private final Group versionGroup = new Group(); public TextField versionField;
    private final Group authorGroup = new Group(); public TextField authorField;
    private final Group widthGroup = new Group(); public Spinner<Integer> widthSpinner;
    private final Group heightGroup = new Group(); public Spinner<Integer> heightSpinner;
    private final Group layersGroup = new Group(); public Spinner<Integer> layersSpinner;

    public PropertiesVue(Coordinate coordinate, int height, int width, LevelDesigner levelDesigner) {
        super(coordinate, height, width);
        this.levelDesigner = levelDesigner;
        this.levelDesigner.addListener(this);
        this.draw();
    }

    public void draw(){
        this.group.getChildren().clear();
        // Draw a background
        Rectangle background = new javafx.scene.shape.Rectangle(
                this.boundingBox.getMinX(), this.boundingBox.getMinY(),
                this.boundingBox.getWidth(), this.boundingBox.getHeight());
        this.group.getChildren().add(background);
        background.setFill(Color.WHITE);
        background.setArcHeight(10);
        background.setArcWidth(10);
        background.setOpacity(0.8);

        // Draw title
        Text title = new Text("Properties");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        title.setLayoutX(this.boundingBox.getMinX() + 10);
        title.setLayoutY(this.boundingBox.getMinY() + 25);

        // Draw properties
        this.drawName(this.levelDesigner.getProperties().getName());
        this.nameGroup.setTranslateX(this.boundingBox.getMinX() + 10);
        this.nameGroup.setTranslateY(this.boundingBox.getMinY() + 40);

        this.drawVersion(this.levelDesigner.getProperties().getVersion());
        this.versionGroup.setTranslateX(this.boundingBox.getMinX() + this.boundingBox.getWidth()/2 - 48);
        this.versionGroup.setTranslateY(this.boundingBox.getMinY() + 40);

        this.drawAuthor(this.levelDesigner.getProperties().getAuthor());
        this.authorGroup.setTranslateX(this.boundingBox.getMinX() + this.boundingBox.getWidth() - 155);
        this.authorGroup.setTranslateY(this.boundingBox.getMinY() + 40);

        this.drawWidth(this.levelDesigner.getCurrentMaze().getProperties().getWidth());
        this.widthGroup.setLayoutX(this.boundingBox.getMinX() + 10);
        this.widthGroup.setLayoutY(this.boundingBox.getMinY() + 80);

        this.drawHeight(this.levelDesigner.getCurrentMaze().getProperties().getHeight());
        this.heightGroup.setLayoutX(this.boundingBox.getMinX() + this.boundingBox.getWidth()/2 - 48);
        this.heightGroup.setLayoutY(this.boundingBox.getMinY() + 80);

        this.drawLayers(this.levelDesigner.getCurrentMaze().getProperties().getLayers());
        this.layersGroup.setLayoutX(this.boundingBox.getMinX() + this.boundingBox.getWidth() - 155);
        this.layersGroup.setLayoutY(this.boundingBox.getMinY() + 80);

        this.group.getChildren().addAll(
                title,
                this.nameGroup, this.versionGroup, this.authorGroup,
                this.widthGroup, this.heightGroup, this.layersGroup
        );
    }

    private void drawName(String name){
        this.nameGroup.getChildren().clear();
        // Add title before text field
        Text text = new Text("Name:");
        text.setTranslateY(20);
        this.nameField = new TextField(name);
        this.nameField .setPrefWidth(100);
        this.nameField .setLayoutX(40);
        this.nameGroup.getChildren().addAll(text, this.nameField );
    }

    private void drawVersion(String version){
        this.versionGroup.getChildren().clear();
        Text text = new Text("Version:");
        text.setTranslateY(20);
        this.versionField = new TextField(version);
        this.versionField.setPrefWidth(50);
        this.versionField.setLayoutX(45);
        this.versionGroup.getChildren().addAll(text, this.versionField);
    }

    private void drawAuthor(String author){
        this.authorGroup.getChildren().clear();
        Text text = new Text("Author:");
        text.setTranslateY(20);
        this.authorField = new TextField(author);
        this.authorField.setPrefWidth(100);
        this.authorField.setLayoutX(45);
        this.authorGroup.getChildren().addAll(text, this.authorField);
    }

    private void drawWidth(int width){
        this.widthGroup.getChildren().clear();
        Text text = new Text("Width:");
        text.setTranslateY(20);
        this.widthSpinner = new Spinner<>(0, 10, 0, 1);
        this.widthSpinner.setPrefWidth(70);
        this.widthSpinner.setLayoutX(45);
        this.widthSpinner.setEditable(true);
        SpinnerValueFactory.IntegerSpinnerValueFactory intFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) this.widthSpinner.getValueFactory();
        intFactory.setMin(10);
        intFactory.setMax(60);
        intFactory.setValue(width);
        this.widthGroup.getChildren().addAll(text, this.widthSpinner);
    }

    private void drawHeight(int height){
        this.heightGroup.getChildren().clear();
        Text text = new Text("Height:");
        text.setTranslateY(20);
        this.heightSpinner = new Spinner<>(0, 10, 0, 1);
        this.heightSpinner.setPrefWidth(70);
        this.heightSpinner.setLayoutX(45);
        this.heightSpinner.setEditable(true);
        SpinnerValueFactory.IntegerSpinnerValueFactory intFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) this.heightSpinner.getValueFactory();
        intFactory.setMin(10);
        intFactory.setMax(60);
        intFactory.setValue(height);
        this.heightGroup.getChildren().addAll(text, this.heightSpinner);
    }

    private void drawLayers(int layers){
        this.layersGroup.getChildren().clear();
        Text text = new Text("Layers:");
        text.setTranslateY(20);
        this.layersSpinner = new Spinner<>(0, 10, 0, 1);
        this.layersSpinner.setPrefWidth(50);
        this.layersSpinner.setLayoutX(45);
        this.layersSpinner.setEditable(true);
        SpinnerValueFactory.IntegerSpinnerValueFactory intFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) this.layersSpinner.getValueFactory();
        intFactory.setMin(1);
        intFactory.setMax(5);
        intFactory.setValue(layers);
        this.layersGroup.getChildren().addAll(text, this.layersSpinner);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("LevelDesigner.currentMazeIndex")) {
            Maze newMaze = this.levelDesigner.getMaze((int) evt.getNewValue());
            this.widthSpinner.getValueFactory().setValue(newMaze.getProperties().getWidth());
            this.heightSpinner.getValueFactory().setValue(newMaze.getProperties().getHeight());
            this.layersSpinner.getValueFactory().setValue(newMaze.getProperties().getLayers());
        }
    }
}
