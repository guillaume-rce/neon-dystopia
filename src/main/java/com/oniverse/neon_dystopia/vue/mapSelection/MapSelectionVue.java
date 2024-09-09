package com.oniverse.neon_dystopia.vue.mapSelection;

import com.oniverse.neon_dystopia.model.Coordinate;
import com.oniverse.neon_dystopia.model.utils.MapsGetter;
import com.oniverse.neon_dystopia.vue.utils.Vue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.InputStream;

public class MapSelectionVue extends Vue {
    private MapSelectorVue mapSelectorVue;
    private final MapsGetter mapsGetter;

    public MapSelectionVue(int height, int width, MapsGetter mapsGetter) {
        super(new Coordinate(0, 0), height, width);
        this.mapsGetter = mapsGetter;
        this.draw();
    }

    @Override
    public void draw() {
        this.group.getChildren().clear();

        // ---- Background ----
        Rectangle background = new Rectangle(
                (int) this.boundingBox.getMinX(), (int) this.boundingBox.getMinY(),
                (int) this.boundingBox.getWidth(), (int) this.boundingBox.getHeight());
        this.group.getChildren().add(background);

        // ---- Title ----
        Text title = new Text("Map selection");
        title.setX(this.boundingBox.getMinX() + 20);
        title.setY(this.boundingBox.getMinY() + 50);
        title.setFont(new Font(40));
        title.setFill(Color.WHITE);
        this.group.getChildren().add(title);

        // ---- Map selector ----
        this.mapSelectorVue = new MapSelectorVue(
                new Coordinate((int) (this.boundingBox.getMinX() + 20), (int) (this.boundingBox.getMinY() + 70)),
                (int) (this.boundingBox.getWidth() - 90), (int) (this.boundingBox.getHeight() - 100),
                this.mapsGetter);
        this.group.getChildren().add(this.mapSelectorVue.getGroup());

        // ---- Import Map Button ----
        Button importButton = new Button("Import map");
        importButton.setLayoutX(this.boundingBox.getMinX() + 40);
        importButton.setLayoutY(this.boundingBox.getMinY() + this.boundingBox.getHeight() - 100);
        this.group.getChildren().add(importButton);

        // Event handling for the button
        importButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Map Folder");
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            if (selectedDirectory != null) {
                // Call a method to handle importing maps from the selected directory
                this.mapsGetter.loadMapsFromDirectory(selectedDirectory);
                // Redraw the map selector with the new maps
                this.mapSelectorVue.update();
            }
        });
    }

    public MapSelectorVue getMapSelectorVue() {
        return this.mapSelectorVue;
    }

    public MapsGetter getMapsGetter() {
        return this.mapsGetter;
    }
}
